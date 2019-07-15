package byteCode.declaration.function;

import byteCode.Node;
import byteCode.symTable.SymTable;
import jdk.internal.org.objectweb.asm.Type;

import java.util.ArrayList;

public abstract class FuncDcl extends Node {
    public Type[] inputs;
    protected Type type;
    protected String name;
    protected String signature;

    public FuncDcl(String returnType, String name, ArrayList<FuncArg> arguments) {
        this.type = SymTable.getTypeFromName(returnType);
        this.name = name;
        inputs = new Type[arguments.size()];
        int i = 0;
        for (FuncArg a : arguments) {
            inputs[i++] = a.getType();
        }


        StringBuilder signature = new StringBuilder();
        signature.append("(");
        for (Type typeIn : inputs) {
            signature.append(typeIn.toString());
        }
        signature.append(")");
        signature.append(type.toString());
        this.signature = signature.toString();

        try {
            SymTable.getInstance().getFunction(name, inputs);
        } catch (RuntimeException r) {
            SymTable.getInstance().addFunction(this);
        }

    }

    public String getSignature() {
        return signature;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public boolean checkIfEqual(Type[] inputs, String name) {

        if (!this.name.equals(name)) {
            return false;
        } else if (this.inputs.length != inputs.length) {
            return false;
        } else {
            int i = 0;
            for (Type t : inputs) {
                if (!this.inputs[i++].equals(t)) {
                    return false;
                }
            }
            return true;
        }
    }
}
