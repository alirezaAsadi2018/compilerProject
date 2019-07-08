package byteCode.exp.binExp.arith;

import byteCode.exp.Exp;
import byteCode.exp.binExp.BinExp;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

import static jdk.internal.org.objectweb.asm.Opcodes.*;
import static jdk.internal.org.objectweb.asm.Opcodes.DSUB;
import static jdk.internal.org.objectweb.asm.Opcodes.ISUB;

public class Minus extends BinExp {
    public Minus(Exp e1, Exp e2) {
        super(e1, e2);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        super.compile(mv, cv);
        if(getType().equals(Type.LONG_TYPE))
            mv.visitInsn(getType().getOpcode(LSUB));
        else if(getType().equals(Type.DOUBLE_TYPE))
            mv.visitInsn(getType().getOpcode(DSUB));
        else
            mv.visitInsn(getType().getOpcode(ISUB));
    }
}
