package byteCode.symTable.dscp;

import jdk.internal.org.objectweb.asm.Type;

public class DscpVarDynamic extends DscpDynamic{

    public DscpVarDynamic(String name, Type type, int index, boolean constant) {
        super(name, type, index,constant);
    }

}
