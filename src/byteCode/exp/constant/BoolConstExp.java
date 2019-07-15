package byteCode.exp.constant;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

public class BoolConstExp extends Constant {
    private Boolean value;

    public BoolConstExp(Boolean value) {
        this.value = value;
        type = Type.BOOLEAN_TYPE;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        IntConstExp.storeIntValue(mv, value ? 1 : 0);
    }
}
