package byteCode.exp.binExp.cond;

import byteCode.exp.Exp;
import byteCode.exp.binExp.BinExp;
import jdk.internal.org.objectweb.asm.*;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public abstract class Cond extends BinExp {
    protected Cond(Exp e1, Exp e2) {
        super(e1, e2);
    }
    protected Cond(){}

    protected void andOr(boolean isAnd, MethodVisitor mv, ClassVisitor cv){
        Label l1 = new Label();
        Label l2 = new Label();
        makeZeroOrOne(e1, mv, cv);
        mv.visitJumpInsn(isAnd ? IFEQ : IFNE, l1);
        makeZeroOrOne(e2, mv, cv);
        mv.visitJumpInsn(isAnd ? IFEQ : IFNE, l1);
        mv.visitInsn(isAnd ? ICONST_1 : ICONST_0);
        mv.visitJumpInsn(GOTO, l2);
        mv.visitLabel(l1);
        mv.visitInsn(isAnd ? ICONST_0 : ICONST_1);
        mv.visitLabel(l2);
    }

}
