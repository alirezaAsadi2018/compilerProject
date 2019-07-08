package byteCode.symTable.dscp;

import java.util.ArrayList;

public class DscpRecStatic extends DscpStatic {
    ArrayList<String> listNames = new ArrayList<String>();

    DscpRecStatic(String name, Class<?> clazz,boolean constant, String... names) {
        super(name, clazz,constant);
        for (String a : names) {
            listNames.add(a);
        }
    }

    DscpRecStatic(String name, String typeS,boolean constant, String... names) {
        super(name, typeS,constant);
        for (String a : names) {
            listNames.add(a);
        }
    }
}
