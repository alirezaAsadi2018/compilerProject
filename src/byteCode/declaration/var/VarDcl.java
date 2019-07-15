package byteCode.declaration.var;

import byteCode.Node;
import byteCode.exp.Exp;
import byteCode.symTable.SymTable;
import byteCode.symTable.dscp.Dscp;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Type;

public abstract class VarDcl extends Node {
    protected String name;
    protected Exp exp = null;
    protected Type type = null;
    protected boolean Constant;

    public Exp getExp() {
        return exp;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        if(type == null)
            throw new RuntimeException("Type is not Set!!!");

        return type;
    }


    public Dscp getDSCP() {
        return SymTable.getInstance().getDescriptor(name);
    }
    public boolean isConstant(){
        return Constant;
    }
    abstract void calculateType();
}
