package byteCode.symTable.dscp;

import java.util.ArrayList;

public class DscpRecDynamic extends DscpDynamic {
    ArrayList<String> listNames = new ArrayList<String>();

    DscpRecDynamic(String name, Class<?> clazz, int index,boolean constant, String... names) {
        super(name, clazz, index,constant);
        for (String a : names) {
            listNames.add(a);
        }
    }

    DscpRecDynamic(String name, String typeS, int index,boolean constant, String... names) {
        super(name, typeS, index,constant);
        this.index = index;
        for (String a : names) {
            listNames.add(a);
        }

    }
}
