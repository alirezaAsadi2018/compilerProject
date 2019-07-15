package byteCode.symTable.dscp;

import jdk.internal.org.objectweb.asm.Type;

public class DscpArrDynamic extends DscpDynamic {
    private int dimensions;

    public DscpArrDynamic(String name, Type type, int index, int dims, boolean constant) {
        super(name, type, index,constant);

        this.dimensions = dims;
    }
}
