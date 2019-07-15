package byteCode.symTable.dscp;

import jdk.internal.org.objectweb.asm.Type;

public class DscpStatic extends Dscp {
    DscpStatic(String name, Type type, boolean constant) {
        super(name, type,constant);
    }
}
