package byteCode.declaration.function;

import byteCode.symTable.SymTable;
import jdk.internal.org.objectweb.asm.Type;

public class FuncArg {
    private Type type;
    private String name;
    private Integer dimensions;

    public FuncArg(String type, String name, Integer dimensions) {
        this.type = SymTable.getTypeFromName(type);
        this.name = name;
        this.dimensions = dimensions;
    }
    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Integer getDimensions() {
        return dimensions;
    }
}
