package byteCode.exp.unExp;

import byteCode.exp.Exp;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class UMin extends UnExp {
    public UMin(Exp e) {
        super(e);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        super.compile(mv, cv);
        if (getType().equals(Type.DOUBLE_TYPE)) {
            mv.visitInsn(e.getType().getOpcode(DNEG));
        } else if (getType().equals(Type.LONG_TYPE)) {
            mv.visitInsn(e.getType().getOpcode(LNEG));
        }else{
            mv.visitInsn(e.getType().getOpcode(INEG));
        }
    }
}
