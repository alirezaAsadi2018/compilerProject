package byteCode.symTable;

import byteCode.declaration.function.ExternFuncDcl;
import byteCode.declaration.function.FuncDcl;
import byteCode.declaration.var.StructDcl;
import byteCode.symTable.dscp.Dscp;
import byteCode.symTable.dscp.DscpVarDynamic;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SymTable {
    public static int FUNCTION = 0;
    public static int LOOP = 2;
    public static int SWITCH = 1;
    public static int COND_OTHER_THAN_SWITCH = 3;
    private static FuncDcl LastSeenFunction;
    private static int labelCounter = 0;
    private static SymTable instance = new SymTable();
    private ArrayList<HashMapExtended<String, Dscp>> stackScopes = new ArrayList<HashMapExtended<String, Dscp>>();
    private HashMap<String, ArrayList<FuncDcl>> funcDcls = new HashMap<String, ArrayList<FuncDcl>>();
    private HashMap<String, StructDcl> recordDcls = new HashMap<>();


    private SymTable() {
        HashMapExtended<String, Dscp> mainFrame = new HashMapExtended<>();
        mainFrame.setIndex(1); //There is Always a (String... args) in main Function.
        stackScopes.add(mainFrame);
    }

    public static FuncDcl getLastSeenFunction() {
        return LastSeenFunction;
    }

    public static void setLastSeenFunction(FuncDcl lastSeenFunction) {
        LastSeenFunction = lastSeenFunction;
    }

    public static Type getTypeFromName(String varType) {
        Type type;
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
            case "short":
                type = Type.SHORT_TYPE;
                break;
            default:
                type = Type.getType("L" + varType + ";");
                //                throw new InvalidDeclaration("Type is not Valid");

        }
        return type;
    }

    public static SymTable getInstance() {
        return instance;
    }

    public Set<String> getKeySet() {
        return funcDcls.keySet();
    }

    public void popScope() {
        stackScopes.remove(stackScopes.size() - 1);
    }

    /**
     * @param name name of type
     * @return int
     * @throws IllegalArgumentException at the seeing of class names which do not exist
     */
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
        //        System.out.println(name);
        //        for(Type t:inputs){
        //            System.out.println(t);
        //        }
        //        System.out.println();
        if (funcDcls.containsKey(name)) {
            ArrayList<FuncDcl> funcDclMapper = funcDcls.get(name);
            for (FuncDcl f : funcDclMapper) {
                if (f.checkIfEqual(inputs, name)) {
                    return f;
                }
            }
            //          TODO make this go away in case of saying something later

            throw new RuntimeException("no such function was found");
        } else {
            //          TODO make this go away in case of saying something later
            throw new RuntimeException("no such function was found");
        }
    }

    public String getNewLabel() {
        return "L" + labelCounter++;
    }

    /**
     * only call this method during compile
     *
     * @param name
     * @return
     */
    public boolean externOrNot(String name) {
        if (!funcDcls.containsKey(name)) {
            throw new RuntimeException("no such funciton");
        }

        return funcDcls.get(name).get(0) instanceof ExternFuncDcl;

    }

    public void addVariable(Dscp dscp, String name) {
        if (getLastFrame().containsKey(name)) {
//            throw new VariableNotFound();
        }
        getLastFrame().put(name, dscp);
        if (dscp instanceof DscpVarDynamic)
            getLastFrame().addIndex(dscp.getType().getSize() - 1);
    }


    public Dscp getDescriptor(String name) { //todo we shall check that we never get all of the stacks out
        int from = stackScopes.size();
        while (from != 0) {
            from--;

            if (stackScopes.get(from).containsKey(name)) {
                return stackScopes.get(from).get(name);
            }
        }
//        throw new VariableNotFound();
        // //////*****alireza ba'dan ezafe shode
        return null;
        ////////*****
    }


    public void addScope(int typeOfScope) {
        HashMapExtended<String, Dscp> frame = new HashMapExtended<>();
        frame.setLabelStart();
        frame.setLabelLast();
        frame.setTypeOfScope(typeOfScope);
        if (typeOfScope != FUNCTION)
            frame.setIndex(getLastFrame().getIndex());
        stackScopes.add(frame);
    }

    public boolean canHaveBreak() {
        return getLastFrame().getTypeOfScope() == LOOP || getLastFrame().getTypeOfScope() == SWITCH;
    }

    /**
     * @return the first empty slot on the last local variable scope
     */

    public void addRecord(StructDcl record) {
        if (recordDcls.containsKey(record.getName()))
//            throw new Redeclaration();

        recordDcls.put(record.getName(), record);
    }

    /**
     * @throws RuntimeException Record Not Found
     */
    public StructDcl getRecord(String name) {
        if (recordDcls.containsKey(name)) {
//            throw new NotFound("Record Not Found");
        }

        return recordDcls.get(name);
    }

    public boolean isRecordDefined(String name){
            getRecord(name);
            return true;
    }


    public int returnNewIndex() {
        return getLastFrame().getAndAddIndex();
    }

    public void setLabelFirst(Label label) {
        getLastFrame().setLabelStart(label);
    }

    public Label getLabelLast() {
        return getLastFrame().getLabelLast();
    }

    public void setLabelLast(Label label) {
        getLastFrame().setLabelLast(label);
    }

    public Label getLabelStart() {
        return getLastFrame().getLabelStart();
    }


    public HashMapExtended<String, Dscp> getLastFrame() {
        if (stackScopes.size() == 0)
            throw new RuntimeException("Something Goes Wrong");

        return stackScopes.get(stackScopes.size() - 1);
    }
}