package byteCode.exp.unExp;

import byteCode.exp.Exp;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class Not extends UnExp{

    public Not(Exp e) {
        super(e);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        super.compile(mv, cv);
        makeZeroOrOne(e, mv, cv);
        mv.visitInsn(ICONST_M1);
        mv.visitInsn(getType().getOpcode(IXOR));
    }
}
