package byteCode.symTable.dscp;

import jdk.internal.org.objectweb.asm.Type;

public class DscpStatic extends Dscp {
    DscpStatic(String name, Type type, boolean constant) {
        super(name, type,constant);
    }

    DscpStatic(String name, Class<?> clazz,boolean constant) {
        super(name, clazz,constant);
    }

    DscpStatic(String name, String typeS,boolean constant) {
        super(name, typeS,constant);
    }
}
