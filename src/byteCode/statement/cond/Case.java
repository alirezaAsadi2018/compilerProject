package byteCode.statement.cond;

import byteCode.block.Block;
import byteCode.exp.constant.IntConstExp;
import byteCode.statement.Statement;
import byteCode.symTable.SymTable;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class Case extends Statement {
    IntConstExp exp;
    Block block;
    Label labelStartCase;
    Label jump;
    Label start;

    public Case(IntConstExp exp, Block block) {
        this.exp = exp;
        this.block = block;
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        labelStartCase = new Label();
        mv.visitLabel(labelStartCase);
        SymTable.getInstance().addScope(SymTable.SWITCH);
        SymTable.getInstance().setLabelLast(jump);
        SymTable.getInstance().setLabelFirst(start);
        block.compile(mv, cv);
        SymTable.getInstance().popScope();
        mv.visitJumpInsn(Opcodes.GOTO, jump);

    }
}
