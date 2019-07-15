package byteCode.symTable.dscp;

import jdk.internal.org.objectweb.asm.Type;

public class DscpArrStatic extends DscpStatic {
    private int dimensions;

    public DscpArrStatic(String name, Type type, int dimensions, boolean constant) {
        super(name, type,constant);
        this.dimensions = dimensions;
    }
}
