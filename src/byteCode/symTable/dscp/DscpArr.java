package byteCode.symTable.dscp;

import jdk.internal.org.objectweb.asm.Type;

public class DscpArr extends DscpDynamic {
    private int dimensions;

    public DscpArr(String name, Type type, int index, int dims, boolean constant) {
        super(name, type, index,constant);

        this.dimensions = dims;
    }
}
