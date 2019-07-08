package byteCode.symTable.dscp;

import jdk.internal.org.objectweb.asm.Type;

public class DscpVarStatic extends DscpStatic {
    public DscpVarStatic(String name, Type type, boolean constant) {
        super(name, type, constant);
    }

    public DscpVarStatic(String name, Class<?> clazz, boolean constant) {
        super(name, clazz, constant);
    }

    public DscpVarStatic(String name, String typeS, boolean constant) {
        super(name, typeS, constant);
    }
}
