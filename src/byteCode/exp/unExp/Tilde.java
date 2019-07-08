package byteCode.exp.unExp;

import byteCode.exp.Exp;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import static jdk.internal.org.objectweb.asm.Opcodes.ICONST_M1;
import static jdk.internal.org.objectweb.asm.Opcodes.IXOR;

public class Tilde extends UnExp {
    public Tilde(Exp e) {
        super(e);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        super.compile(mv, cv);
        mv.visitInsn(ICONST_M1);
        mv.visitInsn(getType().getOpcode(IXOR));
    }
}
