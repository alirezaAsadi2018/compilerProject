package byteCode.exp.var;

import byteCode.exp.Exp;
import byteCode.symTable.SymTable;
import byteCode.symTable.dscp.Dscp;
import jdk.internal.org.objectweb.asm.Type;

public abstract class Var extends Exp {
    String name;
    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return getDSCP().getType();
    }

    public Dscp getDSCP() {
        return SymTable.getInstance().getDescriptor(name);
    }
}
