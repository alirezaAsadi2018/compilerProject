package byteCode.exp.binExp.cond;

import byteCode.exp.Exp;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import static jdk.internal.org.objectweb.asm.Opcodes.IFLE;
import static jdk.internal.org.objectweb.asm.Opcodes.IF_ICMPLE;

public class Gt extends Cond {
    public Gt(Exp e1, Exp e2) {
        super(e1, e2);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        super.compile(mv, cv);
        compare(IFLE, IF_ICMPLE, mv);
    }
}
