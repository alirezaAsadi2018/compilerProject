package byteCode.exp.binExp.cond;

import byteCode.exp.Exp;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class InEq extends Cond {
    public InEq(Exp e1, Exp e2) {
        super(e1, e2);
    }
    public InEq(){

    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        super.compile(mv, cv);

        compare(IFEQ, IF_ICMPLE, mv);
    }
}
