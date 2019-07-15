package byteCode.statement.loop;

import byteCode.block.Block;
import byteCode.exp.Exp;
import byteCode.exp.binExp.cond.InEq;
import byteCode.exp.constant.IntConstExp;
import byteCode.symTable.SymTable;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class Repeat extends Loop {
    Exp exp;

    public Repeat(Block block, Exp exp) {
        super(block);
        this.exp = exp;
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        SymTable.getInstance().addScope(SymTable.LOOP);
        mv.visitLabel(SymTable.getInstance().getLabelStart());
        InEq notEqual = new InEq();
        notEqual.setBinaryExp(exp, new IntConstExp(0));
        notEqual.compile(mv, cv);
        mv.visitJumpInsn(Opcodes.IFEQ, SymTable.getInstance().getLabelLast());
        block.compile(mv, cv);
        mv.visitJumpInsn(Opcodes.GOTO, SymTable.getInstance().getLabelStart());
        mv.visitLabel(SymTable.getInstance().getLabelLast());
        SymTable.getInstance().popScope();
    }
}
