package byteCode.symTable.dscp;

import jdk.internal.org.objectweb.asm.Type;

public class DscpArrStatic extends DscpStatic {
    private int dimensions;

    public DscpArrStatic(String name, Type type, int dimensions, boolean constant) {
        super(name, type,constant);
        this.dimensions = dimensions;
    }

    DscpArrStatic(String name, Class<?> clazz, int dimensions,boolean constant) {
        super(name, clazz,constant);
        this.dimensions = dimensions;
    }

    DscpArrStatic(String name, String typeS, int dimensions,boolean constant) {
        super(name, typeS,constant);
        this.dimensions = dimensions;
    }

    public int getDimensions() {
        return dimensions;
    }
}
