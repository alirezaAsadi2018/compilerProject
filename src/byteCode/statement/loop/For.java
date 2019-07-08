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
    //TODO just remember that for exp2 we have to pop the top value

    /**
     *
     * @param block this is the block that the for will be implementing
     * @param assignment this is the assignment for the first part of the for
     * @param exp1 this is the expression for the middle part of the for
     * @param exp2 this is the expression for the third part of the for
     */
    public For(Block block, Assignment assignment, Exp exp1, Exp exp2){
        //TODO be careful that some parts may be null
        super(block) ;
        this.assignment = assignment;
        this.exp1 = exp1 ;
        this.exp2 = exp2 ;
    }
    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        SymTable.getInstance().addScope(SymTable.LOOP);
        if(assignment !=null){
            assignment.compile(mv,cv);
        }
        mv.visitLabel(SymTable.getInstance().getLabelStart());
        exp1.compile(mv,cv);
        InEq nq = new InEq();
        nq.setBinaryExp(exp1,new IntConstExp(0));
        // 0 : false : jumps to end of for
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
