package byteCode.declaration.var;

import byteCode.exp.Exp;
import byteCode.exp.constant.Constant;
import byteCode.symTable.SymTable;
import byteCode.symTable.dscp.*;
import jdk.internal.org.objectweb.asm.*;
import jdk.internal.org.objectweb.asm.ClassVisitor;

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

    //TODO do something with constant
    public SmplVar(String varName, String varType, Exp value, boolean staticDec, boolean Constant) {
        name = varName;
        this.varType = varType;
        this.exp = value;
        this.staticDec = staticDec;
        this.Constant = Constant;

        if (value == null && varType.equals("auto"))
            throw new RuntimeException("auto must have value!");
    }

    /**
     * @param staticDec check if it is static or not
     * @param type      Type of Variable
     * @throws IllegalArgumentException if type and name is not set!
     */
    private void declare(boolean staticDec, Type type, boolean Constant) {
        // TODO: 01/07/2018 SymTable Should Change

        if (name == null || type == null)
            throw new IllegalArgumentException();

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
        calculateType(mv,cv);
        declare(staticDec, type, Constant);
        Dscp dscp = getDSCP();
        if (dscp instanceof DscpStatic) {
            addFieldToClass(cv, true);
        } else {
            DscpDynamic dscpDynamic = (DscpDynamic) dscp;
            if (getExp() != null && getExp().getType().equals(getType())) {
                getExp().compile(mv, cv);
                mv.visitVarInsn(getType().getOpcode(ISTORE), dscpDynamic.getIndex());
            }
//            else{
//                mv.visitInsn(getType().getOpcode(Opcodes.ICONST_0));
//                mv.visitVarInsn(getType().getOpcode(ISTORE), dscpDynamic.getIndex());
//            }
        }
    }

    @Override
    void calculateType(MethodVisitor mv, ClassVisitor cv) {
        // TODO: 02/07/2018 Check is correct for Records
        //Just a Trick :)
        ClassVisitor dcv = new ClassVisitor(327680){};
        MethodVisitor dmv = new MethodVisitor(327680) {};

        if (!varType.equals("auto"))
            type = getTypeFromName(varType);
        else {
            getExp().compile(dmv, dcv);
            type = getExp().getType();
        }
    }

    @Override
    public Exp getExp() {
        return super.getExp();
    }

    @Override
    public void addFieldToClass(ClassVisitor cv, boolean isStatic){
        Object value = null;
        // TODO: 01/07/2018 Handle Not Constant
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
