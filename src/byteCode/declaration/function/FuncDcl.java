package byteCode.declaration.function;

import byteCode.Node;
import byteCode.symTable.SymTable;
import jdk.internal.org.objectweb.asm.Type;

import java.util.ArrayList;

public abstract class FuncDcl extends Node {
    protected Type type;
    protected String name;
    protected String signature;
    public Type [] inputs;
    public String getSignature(){
        return signature;
    }
    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public FuncDcl(String type1, String name, ArrayList<FuncArg> arguments) {
        this.type = SymTable.getTypeFromName(type1);
        this.name = name;
        inputs = new Type[arguments.size()];
        int i = 0;
        for(FuncArg f : arguments){
            inputs[i++]=f.getType();
        }


        String signature = "";
        signature = signature + "(";
        for(Type typeIn : inputs){
            signature = signature+typeIn.toString();
        }
        signature = signature + ")";
        signature = signature + type.toString();
        this.signature = signature;

        try {
            SymTable.getInstance().getFunction(name,inputs);
        }catch (RuntimeException r){
            SymTable.getInstance().addFunction(this);
        }

    }

    public FuncDcl(String functionSignature, String name) {
        this.signature = functionSignature;
        Type[] types = Type.getArgumentTypes(functionSignature);
        Type [] arguments = types;
        this.type = Type.getType(functionSignature.substring(functionSignature.indexOf(')')+1));
        this.name = name;
        inputs = new Type[arguments.length];
        int i = 0;
        for(Type t : arguments){
            inputs[i++]=t;
        }



        try {
            SymTable.getInstance().getFunction(name,inputs);
        }catch (RuntimeException r){
            SymTable.getInstance().addFunction(this);
        }

    }

    public boolean checkIfEqual(Type [] inputs, String name){

        if (!this.name.equals(name)){
            return false;
        }else if (this.inputs.length!=inputs.length){
            return false;
        }else {
            int i = 0;
            for(Type t : inputs){
                if(!this.inputs[i++].equals(t)){
                    return false;
                }
            }
            return true;
        }
    }
}
