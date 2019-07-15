package byteCode.statement.loop;

import byteCode.block.Block;
import byteCode.exp.Exp;
import byteCode.statement.assignments.Assignment;
import byteCode.exp.binExp.cond.InEq;
import byteCode.exp.constant.IntConstExp;
import byteCode.symTable.SymTable;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class For extends Loop {
    Assignment assignment;
    Exp exp1;
    Exp exp2;
    public For(Block block, Assignment assignment, Exp exp1, Exp exp2){
        super(block) ;
        this.assignment = assignment;
        this.exp1 = exp1 ;
        this.exp2 = exp2 ;
    }
    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        SymTable.getInstance().addScope(SymTable.LOOP);
        if(assignment != null){
            assignment.compile(mv,cv);
        }
        mv.visitLabel(SymTable.getInstance().getLabelStart());
        exp1.compile(mv,cv);
        mv.visitJumpInsn(Opcodes.IFEQ,SymTable.getInstance().getLabelLast());

        if(exp2!=null){
            exp2.compile(mv,cv);
            mv.visitInsn(Opcodes.POP);
        }

        block.compile(mv,cv);
        mv.visitJumpInsn(Opcodes.GOTO,SymTable.getInstance().getLabelStart());
        mv.visitLabel(SymTable.getInstance().getLabelLast());
        SymTable.getInstance().popScope();
    }
}
