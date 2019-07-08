package byteCode.exp.constant;
import byteCode.symTable.SymTable;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

public class SizeOf extends Constant{
    private Integer value;

    public SizeOf(String id) {
        type = Type.INT_TYPE;
        this.value = SymTable.getInstance().getSize(id);
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        IntConstExp.storeIntValue(mv, value);
    }
}
