package byteCode.statement.assignments;

import byteCode.exp.Exp;
import byteCode.exp.var.SmplVarExp;
import byteCode.exp.var.Var;
import byteCode.symTable.dscp.Dscp;
import byteCode.symTable.dscp.DscpDynamic;
import byteCode.util.DefinedValues;
import byteCode.util.UtilFunctions;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

import static jdk.internal.org.objectweb.asm.Opcodes.ISTORE;

public class EqAssign extends Assignment {


    public EqAssign(Var v, Exp e) {
        super(v, e);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        this.type = var.getType();

        // TODO: 29/06/2018 handle Arrays
        if (!(var instanceof SmplVarExp))
            throw new RuntimeException();

        Dscp dscp = var.getDSCP();
        exp.compile(mv,cv);
        if (var.getType() != exp.getType())
            UtilFunctions.cast(exp.getType(), var.getType(), mv, cv);
        if(dscp instanceof DscpDynamic) {
            int index = ((DscpDynamic) dscp).getIndex();
            if(var.getType().equals(Type.DOUBLE_TYPE)){
                mv.visitVarInsn(var.getType().getOpcode(ISTORE), index);
                var.compile(mv,cv);
            }else{
                mv.visitVarInsn(var.getType().getOpcode(ISTORE), index);
                var.compile(mv,cv);
            }
        }else{
            // TODO: 29/06/2018 For Static Variables;
            mv.visitFieldInsn(Opcodes.PUTSTATIC, DefinedValues.nameClass, dscp.getName(), dscp.getType().toString());
//            throw new RuntimeException();
        }


    }
}
