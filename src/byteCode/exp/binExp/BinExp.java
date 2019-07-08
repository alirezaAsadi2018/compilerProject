package byteCode.exp.binExp;

import byteCode.exp.Exp;
import byteCode.util.DefinedValues;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;


public abstract class BinExp extends Exp {
    protected Exp e1, e2;

    protected BinExp(Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    protected BinExp(){}

    @Override
    public Type getType(){
        if (!e1.getType().equals(e2.getType()))
            throw new IllegalArgumentException("Two Operands must be of the same type");
        return e1.getType();
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        e1.compile(mv, cv);
        e2.compile(mv, cv);

    }
    public BinExp setBinaryExp(Exp exp1, Exp exp2){
        if(DefinedValues.DEBUG){
            System.out.println("hey");
        }
        this.e1=exp1;
        this.e2=exp2;
        return this;
    }
}
