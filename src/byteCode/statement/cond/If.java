package byteCode.statement.cond;

import byteCode.block.Block;
import byteCode.exp.Exp;
import byteCode.exp.binExp.cond.InEq;
import byteCode.exp.constant.IntConstExp;
import byteCode.statement.Statement;
import byteCode.symTable.SymTable;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class If extends Statement {
    Exp exp;
    Block block1, block2;

    public If(Exp exp, Block block1, Block block2) {
        this.exp = exp;
        this.block1 = block1;
        this.block2 = block2;
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        SymTable.getInstance().addScope(SymTable.COND_OTHER_THAN_SWITCH);
        InEq notEqual = new InEq();
        notEqual.setBinaryExp(exp, new IntConstExp(0));
        notEqual.compile(mv, cv);
        Label startElse = new Label();
        Label endElse = new Label();
        mv.visitJumpInsn(Opcodes.IFEQ, startElse);
        block1.compile(mv, cv);
        mv.visitJumpInsn(Opcodes.GOTO, endElse);
        if (block2 != null) {
            SymTable.getInstance().popScope();
            SymTable.getInstance().addScope(SymTable.COND_OTHER_THAN_SWITCH);
            SymTable.getInstance().setLabelFirst(startElse);
            SymTable.getInstance().setLabelLast(endElse);
            mv.visitLabel(startElse);
            block2.compile(mv, cv);
            mv.visitLabel(endElse);
        } else {
            mv.visitLabel(startElse);
            mv.visitLabel(endElse);
        }
    }
}
