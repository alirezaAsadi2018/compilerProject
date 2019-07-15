package byteCode.exp.var;

import byteCode.exp.Exp;
import byteCode.symTable.dscp.Dscp;
import byteCode.symTable.dscp.DscpDynamic;
import byteCode.util.DefinedValues;
import byteCode.util.UtilFunctions;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.util.List;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class ArrVarExp extends Var {
    private List<Exp> arguments;

    public ArrVarExp(String varName, List<Exp> arguments) {
        name = varName;
        this.arguments = arguments;
    }

    public void loadReferenceAndIndex(MethodVisitor mv, ClassVisitor cv){
        Dscp dscp = getDSCP();
        if (dscp instanceof DscpDynamic) {
            int index = ((DscpDynamic) getDSCP()).getIndex();
            mv.visitVarInsn(ALOAD, index);
        } else {
            mv.visitFieldInsn(GETSTATIC, DefinedValues.nameClass, getName(), getType().getDescriptor());
        }


        for (int i = 0; i < arguments.size() - 1; i++) {
            arguments.get(i).compile(mv, cv);
            if (!UtilFunctions.isInteger(arguments.get(i).getType())) {
                throw new RuntimeException("Bad Index Type");
            }
            mv.visitInsn(AALOAD);
        }
        arguments.get(arguments.size()-1).compile(mv,cv);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        loadReferenceAndIndex(mv,cv);
        mv.visitInsn(getType().getElementType().getOpcode(IALOAD));
    }

}
