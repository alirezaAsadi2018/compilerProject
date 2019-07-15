package byteCode.exp.constant;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

import static jdk.internal.org.objectweb.asm.Opcodes.DCONST_0;
import static jdk.internal.org.objectweb.asm.Opcodes.DCONST_1;

public class RealConstExp extends Constant {
    private Double value;

    public RealConstExp(Double value) {
        this.value = value;
        type = Type.DOUBLE_TYPE;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        if (value == 0)
            mv.visitInsn(DCONST_0);
        else if (value == 1)
            mv.visitInsn(DCONST_1);
        else
            mv.visitLdcInsn(value);
    }
}
