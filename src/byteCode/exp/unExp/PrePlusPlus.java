package byteCode.exp.unExp;


import byteCode.exp.Exp;
import byteCode.exp.var.SmplVarExp;
import byteCode.exp.var.Var;
import byteCode.symTable.dscp.Dscp;
import byteCode.symTable.dscp.DscpDynamic;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class PrePlusPlus extends UnExp {
    public PrePlusPlus(Exp e) {
        super(e);
    }

    public PrePlusPlus() {
        super();
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        if (!(e instanceof Var) || (type != Type.INT_TYPE && type != Type.DOUBLE_TYPE && type != Type.LONG_TYPE && type != Type.CHAR_TYPE))
            throw new IllegalArgumentException();


        Var var = (Var) e;
        type = e.getType();

        // TODO: 29/06/2018 For Array And Records Should Be Added !!
        if (e instanceof SmplVarExp) {
            Dscp dscp = var.getDSCP();

            if (dscp instanceof DscpDynamic) {
                int index = ((DscpDynamic) dscp).getIndex();
                if (type == Type.INT_TYPE || type == Type.CHAR_TYPE) {
                    mv.visitIincInsn(index, 1);
                    var.compile(mv,cv); //Prefix
                }
                else {
                    if (type == Type.DOUBLE_TYPE) {
                        mv.visitInsn(DCONST_1);
                    }
                    else {
                        mv.visitInsn(LCONST_1);
                    }

                    var.compile(mv,cv);
                    mv.visitInsn(type.getOpcode(IADD));
                    mv.visitVarInsn(type.getOpcode(ISTORE), index);

                    var.compile(mv,cv); //Prefix
                }

            } else {
                // TODO: 29/06/2018 For Static Variables
                throw new RuntimeException();
            }
        }
    }
}
