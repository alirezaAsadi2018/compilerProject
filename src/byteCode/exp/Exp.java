package byteCode.exp;
import byteCode.Node;
import jdk.internal.org.objectweb.asm.*;

import static jdk.internal.org.objectweb.asm.Opcodes.*;
import static jdk.internal.org.objectweb.asm.Opcodes.IF_ICMPLE;

public abstract class Exp extends Node {
    protected Type type = null;

    //TODO should do more and over ride for type checking
    public Type getType() {
        if(type == null)
            throw new RuntimeException("Type is Not Set!!!");

        return type;
    }
    protected void makeZeroOrOne(Exp e, MethodVisitor mv, ClassVisitor cv){
        e.compile(mv, cv);
        if (getType().equals(Type.DOUBLE_TYPE)) {
            mv.visitInsn(DCONST_0);
        } else if (getType().equals(Type.LONG_TYPE)) {
            mv.visitInsn(LCONST_0);
        }else{
            mv.visitInsn(ICONST_0);
        }
        compare(IFEQ, IF_ICMPLE, mv);
    }
    protected void compare(int notIntOpcode, int intOpcode, MethodVisitor mv) {
        int opcode = notIntOpcode;
        if (getType().equals(Type.DOUBLE_TYPE)) {
            mv.visitInsn(Opcodes.DCMPG);
        } else if (getType().equals(Type.LONG_TYPE)) {
            mv.visitInsn(Opcodes.LCMP);
        }else{
            opcode = intOpcode;
        }
        Label l1 = new Label();
        Label l2 = new Label();
        mv.visitJumpInsn(opcode, l1);
        mv.visitInsn(ICONST_1);
        mv.visitJumpInsn(GOTO, l2);
        mv.visitLabel(l1);
        mv.visitInsn(ICONST_0);
        mv.visitLabel(l2);
    }
}
