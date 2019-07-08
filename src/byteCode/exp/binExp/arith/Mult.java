package byteCode.exp.binExp.arith;

import byteCode.exp.Exp;
import byteCode.exp.binExp.BinExp;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class Mult extends BinExp {
    public Mult(Exp e1, Exp e2) {
        super(e1, e2);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        super.compile(mv, cv);
        if (getType().equals(Type.LONG_TYPE))
            mv.visitInsn(getType().getOpcode(LMUL));
        else if (getType().equals(Type.DOUBLE_TYPE))
            mv.visitInsn(getType().getOpcode(DMUL));
        else
            mv.visitInsn(getType().getOpcode(IMUL));
    }
}
