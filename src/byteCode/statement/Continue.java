package byteCode.statement;

import byteCode.symTable.SymTable;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class Continue extends Statement {
    public Continue(){

    }

    //    TODO this asserts that you have pushed the right
    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        if (SymTable.getInstance().canHaveBreak()){
            mv.visitJumpInsn(Opcodes.GOTO, SymTable.getInstance().getLabelStart());
        }else {
            throw new RuntimeException("This part is not switch nor Loop");
        }
    }
}
