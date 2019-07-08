package byteCode.exp.unExp;

import byteCode.exp.Exp;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

public abstract class UnExp extends Exp {
    protected Exp e;

    protected UnExp(Exp e) {
        this.e = e;
    }

    public UnExp() {

    }

    @Override
    public Type getType(){
        return e.getType();
    }
    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        e.compile(mv, cv);
    }
}
