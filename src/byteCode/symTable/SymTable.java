package byteCode.symTable;

import byteCode.declaration.function.FuncDcl;
import byteCode.symTable.dscp.Dscp;
import byteCode.symTable.dscp.DscpVar;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.HashMap;

public class SymTable {
    public static int FUNCTION = 0;
    public static int LOOP = 2;
    public static int SWITCH = 1;
    public static int COND_OTHER_THAN_SWITCH = 3;
    private static FuncDcl LastSeenFunction;
    private static SymTable instance = new SymTable();
    private ArrayList<HashMapExtended<String, Dscp>> stackScopes = new ArrayList<>();
    private HashMap<String, ArrayList<FuncDcl>> funcDcls = new HashMap<>();


    private SymTable() {
        HashMapExtended<String, Dscp> mainFrame = new HashMapExtended<>();
        mainFrame.index = 1;
        stackScopes.add(mainFrame);
    }

    public static FuncDcl getLastSeenFunction() {
        return LastSeenFunction;
    }

    public static void setLastSeenFunction(FuncDcl lastSeenFunction) {
        LastSeenFunction = lastSeenFunction;
    }

    public static Type getTypeFromName(String varType) {
        Type type = null;
        switch (varType) {
            case "int":
                type = Type.INT_TYPE;
                break;
            case "long":
                type = Type.LONG_TYPE;
                break;
            case "char":
                type = Type.CHAR_TYPE;
                break;
            case "bool":
                type = Type.BOOLEAN_TYPE;
                break;
            case "double":
                type = Type.DOUBLE_TYPE;
                break;
            case "float":
                type = Type.FLOAT_TYPE;
                break;
            case "string":
                type = Type.getType(String.class);
                break;
            case "void":
                type = Type.VOID_TYPE;
                break;
        }
        return type;
    }

    public static SymTable getInstance() {
        return instance;
    }

    public void popScope() {
        stackScopes.remove(stackScopes.size() - 1);
    }

    public int getSize(String name) {
        int size;
        switch (name) {
            case "int":
                size = Integer.SIZE;
                break;
            case "long":
                size = Long.SIZE;
                break;
            case "char":
                size = Character.SIZE;
                break;
            case "bool":
                size = 1;
                break;
            case "double":
                size = Double.SIZE;
                break;
            case "float":
                size = Float.SIZE;
                break;
            case "string":
                size = Integer.SIZE;
                break;
            default:
                throw new IllegalArgumentException("Type is not Valid");

        }
        return size;
    }

    public void addFunction(FuncDcl funcDcl) {
        if (funcDcls.containsKey(funcDcl.getName())) {
            funcDcls.get(funcDcl.getName()).add(funcDcl);
        } else {
            ArrayList<FuncDcl> funcDclMapper = new ArrayList<>();
            funcDclMapper.add(funcDcl);
            funcDcls.put(funcDcl.getName(), funcDclMapper);
        }
    }

    public FuncDcl getFunction(String name, Type[] inputs) {
        if (funcDcls.containsKey(name)) {
            ArrayList<FuncDcl> funcDclMapper = funcDcls.get(name);
            for (FuncDcl f : funcDclMapper) {
                if (f.checkIfEqual(inputs, name)) {
                    return f;
                }
            }
            throw new RuntimeException("");
        } else {
            throw new RuntimeException("");
        }
    }


    public void addVariable(Dscp dscp, String name) {
        if (getLastFrame().containsKey(name)) {
            throw new IllegalArgumentException("variable already declared");
        }
        getLastFrame().put(name, dscp);
        if (dscp instanceof DscpVar)
            getLastFrame().index += dscp.getType().getSize() - 1;
    }


    public Dscp getDescriptor(String name) {
        int from = stackScopes.size();
        while (from != 0) {
            from--;

            if (stackScopes.get(from).containsKey(name)) {
                return stackScopes.get(from).get(name);
            }
        }
        throw new RuntimeException("variable not defined");
    }


    public void addScope(int typeOfScope) {
        HashMapExtended<String, Dscp> frame = new HashMapExtended<>();
        frame.typeOfScope = typeOfScope;
        if (typeOfScope != FUNCTION)
            frame.index = getLastFrame().index;
        stackScopes.add(frame);
    }

    public boolean canHaveBreak() {
        return getLastFrame().typeOfScope == LOOP || getLastFrame().typeOfScope == SWITCH;
    }



    public int returnNewIndex() {
        return getLastFrame().index++;
    }

    public void setLabelFirst(Label label) {
        getLastFrame().labelStart = label;
    }

    public Label getLabelLast() {
        return getLastFrame().labelLast;
    }

    public void setLabelLast(Label label) {
        getLastFrame().labelLast = label;
    }

    public Label getLabelStart() {
        return getLastFrame().labelStart;
    }


    public HashMapExtended<String, Dscp> getLastFrame() {
        if (stackScopes.size() == 0)
            throw new RuntimeException("");

        return stackScopes.get(stackScopes.size() - 1);
    }
}