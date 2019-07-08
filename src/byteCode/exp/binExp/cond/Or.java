package byteCode.exp.binExp.cond;

import byteCode.exp.Exp;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public class Or extends Cond {
    public Or(Exp e1, Exp e2) {
        super(e1, e2);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        andOr(false, mv, cv);
    }
}
