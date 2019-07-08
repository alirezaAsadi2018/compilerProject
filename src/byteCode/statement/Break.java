package byteCode.statement;

import byteCode.symTable.SymTable;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class Break extends Statement {
    public Break(){

    }
    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        if(SymTable.getInstance().canHaveBreak()){
            mv.visitJumpInsn(Opcodes.GOTO, SymTable.getInstance().getLabelLast());
        }else {
            throw new RuntimeException("This part is not switch nor Loop");
        }
    }
}
