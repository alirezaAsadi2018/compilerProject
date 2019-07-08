package byteCode.symTable.dscp;

import jdk.internal.org.objectweb.asm.Type;

public class DscpArrDynamic extends DscpDynamic {
    private int dimensions;

    public DscpArrDynamic(String name, Type type, int index, int dims, boolean constant) {
        super(name, type, index,constant);

        this.dimensions = dims;
    }

    DscpArrDynamic(String name, Class<?> clazz, int index, int dims,boolean constant) {
        super(name, clazz, index,constant);

        this.dimensions = dims;
    }

    DscpArrDynamic(String name, String typeS, int index, int dims,boolean constant) {
        super(name, typeS, index,constant);

        this.dimensions = dims;
    }

    public int getDimensions() {
        return dimensions;
    }
}
