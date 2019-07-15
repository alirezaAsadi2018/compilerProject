package byteCode.symTable;

import jdk.internal.org.objectweb.asm.Label;

import java.util.HashMap;

public class HashMapExtended<K,V> extends HashMap<K,V> {
    public int index = 0;
    public Label labelStart = new Label();
    public Label labelLast = new Label();
    public int typeOfScope;
}