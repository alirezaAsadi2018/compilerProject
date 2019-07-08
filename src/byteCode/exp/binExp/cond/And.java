package byteCode.exp.binExp.cond;

import byteCode.exp.Exp;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class And extends Cond {
    public And(Exp e1, Exp e2) {
        super(e1, e2);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        andOr(true, mv, cv);
    }
}
