package byteCode.declaration.var;

import byteCode.exp.Exp;
import byteCode.symTable.SymTable;
import byteCode.symTable.dscp.Dscp;
import byteCode.symTable.dscp.DscpDynamic;
import byteCode.symTable.dscp.DscpVar;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

import static byteCode.symTable.SymTable.getTypeFromName;
import static jdk.internal.org.objectweb.asm.Opcodes.ISTORE;

public class SmplVar extends VarDcl {
    String varType;

    public SmplVar(String varName, String varType, boolean Constant) {
        name = varName;
        this.varType = varType;
        this.Constant = Constant;

    }

    public SmplVar(String varName, String varType, Exp value, boolean Constant) {
        name = varName;
        this.varType = varType;
        this.exp = value;
        this.Constant = Constant;

    }


    private void declare(Type type, boolean Constant) {
        Dscp dscp;
        dscp = new DscpVar(name, type, SymTable.getInstance().returnNewIndex(), Constant);
        SymTable.getInstance().addVariable(dscp, name);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        calculateType(); // find asm.Type from string
        declare(type, Constant); // adds to symbol table
        Dscp dscp = getDSCP();
        DscpDynamic dscpDynamic = (DscpDynamic) dscp;
        if (getExp() != null && getExp().getType().equals(getType())) {
            getExp().compile(mv, cv);
            mv.visitVarInsn(getType().getOpcode(ISTORE), dscpDynamic.getIndex());
        }
    }

    @Override
    void calculateType() {
        ClassVisitor dcv = new ClassVisitor(327680) {
        }; // another cv for compiling code right now
        MethodVisitor dmv = new MethodVisitor(327680) {
        }; // another mv for compiling code right now

        if (!varType.equals("auto"))
            type = getTypeFromName(varType);
        else {
            getExp().compile(dmv, dcv);
            type = getExp().getType();
        }
    }

}
