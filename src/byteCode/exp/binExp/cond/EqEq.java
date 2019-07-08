package byteCode.exp.binExp.cond;

import byteCode.exp.Exp;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import static jdk.internal.org.objectweb.asm.Opcodes.IFNE;
import static jdk.internal.org.objectweb.asm.Opcodes.IF_ICMPNE;


public class EqEq extends Cond {
    public EqEq(Exp e1, Exp e2) {
        super(e1, e2);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        super.compile(mv, cv);

        compare(IFNE, IF_ICMPNE, mv);
    }
}
