package byteCode.symTable.dscp;

import jdk.internal.org.objectweb.asm.Type;

public class Dscp {
    Type type;
    String name;
    boolean isConstant;

    public Dscp(String name, Type type, boolean constant) {
        this.type = type;
        this.name = name;
        this.isConstant = constant;
    }

    public Type getType() {
        return type;
    }

    public boolean isConstant() {
        return isConstant;
    }

    public String getName() {
        return name;
    }
}
