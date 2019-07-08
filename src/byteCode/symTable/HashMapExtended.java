package byteCode.symTable;

import jdk.internal.org.objectweb.asm.Label;

import java.util.HashMap;

public class HashMapExtended<K,V> extends HashMap<K,V> {
    private int index = 0;
    private Label labelStart ;
    private Label labelLast;
    private int typeOfScope;
    public Label getLabelLast() {
        return labelLast;
    }

    public void setLabelLast() {
        this.labelLast = new Label();
    }
    public void setLabelLast(Label label) {
        this.labelStart = label;
    }


    public Label getLabelStart() {
        return labelStart;
    }

    public void setLabelStart() {
        this.labelStart = new Label();
    }
    public void setLabelStart(Label label) {
        this.labelStart = label;
    }





    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public int getAndAddIndex(){
        return index++;
    }
    public void addIndex(int add){
        index += add;
    }

    public int getTypeOfScope() {
        return typeOfScope;
    }

    public void setTypeOfScope(int typeOfScope) {
        this.typeOfScope = typeOfScope;
    }
}