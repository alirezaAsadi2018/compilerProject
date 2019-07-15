package byteCode.exp.var;

import byteCode.symTable.dscp.Dscp;
import byteCode.symTable.dscp.DscpDynamic;
import byteCode.util.DefinedValues;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class SmplVarExp extends Var {


    public SmplVarExp(String varName) {
        name = varName;
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        type = getType();
        Dscp dscp = getDSCP();
        if (dscp instanceof DscpDynamic) {
            int index = ((DscpDynamic) dscp).getIndex();
            mv.visitVarInsn(getType().getOpcode(ILOAD), index);
        } else {
            mv.visitFieldInsn(GETSTATIC, DefinedValues.nameClass, getName(), getType().getDescriptor());
        }
    }

    /*
     * Store Value From Stack;
     */
    public void compileStoreValue(MethodVisitor mv, ClassVisitor cv) {
        Dscp dscp = getDSCP();
        if (dscp instanceof DscpDynamic) {
            int index = ((DscpDynamic) dscp).getIndex();
            mv.visitVarInsn(getType().getOpcode(ISTORE), index);
        } else {
            mv.visitFieldInsn(PUTSTATIC, DefinedValues.nameClass, getName(), getType().getDescriptor());
        }
    }
}
