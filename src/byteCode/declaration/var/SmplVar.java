package byteCode.declaration.var;

import byteCode.exp.Exp;
import byteCode.exp.constant.Constant;
import byteCode.symTable.SymTable;
import byteCode.symTable.dscp.*;
import jdk.internal.org.objectweb.asm.*;

import static byteCode.symTable.SymTable.getTypeFromName;
import static jdk.internal.org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static jdk.internal.org.objectweb.asm.Opcodes.ISTORE;

public class SmplVar extends VarDcl {
    boolean staticDec;
    String varType;

    public SmplVar(String varName, String varType, boolean staticDec, boolean Constant) {
        name = varName;
        this.varType = varType;
        this.staticDec = staticDec;
        this.Constant = Constant;

    }

    public SmplVar(String varName, String varType, Exp value, boolean staticDec, boolean Constant) {
        name = varName;
        this.varType = varType;
        this.exp = value;
        this.staticDec = staticDec;
        this.Constant = Constant;

    }


    private void declare(boolean staticDec, Type type, boolean Constant) {
        Dscp dscp;
        if (staticDec) {
            dscp = new DscpVarStatic(name, type, Constant);
        } else {
            dscp = new DscpVarDynamic(name, type, SymTable.getInstance().returnNewIndex(), Constant);
        }

        SymTable.getInstance().addVariable(dscp, name);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        calculateType(); // find asm.Type from string
        declare(staticDec, type, Constant); // adds to symbol table
        Dscp dscp = getDSCP();
        if (dscp instanceof DscpStatic) {
            addFieldToClass(cv, true);
        } else {
            DscpDynamic dscpDynamic = (DscpDynamic) dscp;
            if (getExp() != null && getExp().getType().equals(getType())) {
                getExp().compile(mv, cv);
                mv.visitVarInsn(getType().getOpcode(ISTORE), dscpDynamic.getIndex());
            }
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


    @Override
    public void addFieldToClass(ClassVisitor cv, boolean isStatic) {
        Object value = null;
        if (getExp() instanceof Constant && getExp().getType().equals(getType())) {
            value = ((Constant) getExp()).getValue();
        }
        int access = ACC_PUBLIC;
        access += isConstant() ? Opcodes.ACC_FINAL : 0;
        access += isStatic ? Opcodes.ACC_STATIC : 0;

        FieldVisitor fv = cv.visitField(access, getName(), getType().getDescriptor(), null, value);
        fv.visitEnd();
    }
}
