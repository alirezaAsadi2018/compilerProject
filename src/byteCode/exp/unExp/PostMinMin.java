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

public class PostMinMin extends UnExp {


    public PostMinMin(Exp e) {
        super(e);
    }

    public PostMinMin() {

    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        {
            {

                if (!(e instanceof Var) || (type != Type.INT_TYPE && type != Type.DOUBLE_TYPE && type != Type.LONG_TYPE && type != Type.CHAR_TYPE))
                    throw new IllegalArgumentException();


                Var var = (Var) e;

                type = var.getType();

                if (e instanceof SmplVarExp) {
                    Dscp dscp = var.getDSCP();

                    if (dscp instanceof DscpDynamic) {
                        int index = ((DscpDynamic) dscp).getIndex();
                        if (type == Type.INT_TYPE || type == Type.CHAR_TYPE) {
                            var.compile(mv, cv);
                            mv.visitIincInsn(index, -1);
                        } else {
                            var.compile(mv, cv);//Postfix

                            var.compile(mv, cv);

                            if (type == Type.DOUBLE_TYPE) {
                                mv.visitInsn(DCONST_1);
                            } else {
                                mv.visitInsn(LCONST_1);
                            }

                            mv.visitInsn(type.getOpcode(ISUB));
                            mv.visitVarInsn(type.getOpcode(ISTORE), index);
                        }

                    } else {
                        throw new RuntimeException();
                    }
                }
            }
        }

    }

}
