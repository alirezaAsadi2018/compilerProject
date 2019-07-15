package byteCode.symTable.dscp;

import jdk.internal.org.objectweb.asm.Type;

public class DscpVarStatic extends DscpStatic {
    public DscpVarStatic(String name, Type type, boolean constant) {
        super(name, type, constant);
    }
}
