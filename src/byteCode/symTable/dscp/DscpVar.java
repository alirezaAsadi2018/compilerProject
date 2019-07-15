package byteCode.symTable.dscp;

import jdk.internal.org.objectweb.asm.Type;

public class DscpVar extends DscpDynamic{

    public DscpVar(String name, Type type, int index, boolean constant) {
        super(name, type, index,constant);
    }

}
