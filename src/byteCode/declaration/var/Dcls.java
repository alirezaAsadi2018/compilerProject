package byteCode.declaration.var;

import byteCode.Node;
import byteCode.util.DefinedValues;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;

public class Dcls extends Node {
    public ArrayList<SpecClass> specClasses;
    public String type;
    public boolean Constant;
    ArrayList<VarDcl> dcls = new ArrayList<>();

    public Dcls(ArrayList<SpecClass> specClasses, String type, boolean Constant) {
        this.specClasses = specClasses;
        this.type = type;
        this.Constant = Constant;


    }

    public ArrayList<VarDcl> getVariables() {
        return dcls;
    }

    //TODO keep in mind that the type maybe of struct
    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        for (SpecClass c : this.specClasses) {
            if (c.value == null) {
                if (c.dims == null || c.dims.size() == 0) {
                    dcls.add(new SmplVar(c.name, type, DefinedValues.getScope(), Constant));
                }else{
                    dcls.add(new ArrVarDcl(c.name, type, c.dims, DefinedValues.getScope(), Constant));
                }
            }else{
                dcls.add(new SmplVar(c.name, type, c.value, DefinedValues.getScope(), Constant));
            }
        }
        for (VarDcl v : dcls) {
            v.compile(mv, cv);
        }
    }
}
