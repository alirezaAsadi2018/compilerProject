package byteCode.symTable.dscp;

import jdk.internal.org.objectweb.asm.Type;

public class DscpDynamic extends Dscp{
    int index;

    DscpDynamic(String name, Type type, int index, boolean constant) {
        super(name, type, constant);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
