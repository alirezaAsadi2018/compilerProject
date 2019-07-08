package byteCode.statement.cond;

import byteCode.block.Block;
import byteCode.exp.Exp;
import byteCode.statement.Statement;
import byteCode.symTable.SymTable;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.util.ArrayList;

public class Switch extends Statement {
    Exp exp;
    ArrayList<Case> cases;
    Block blockDefault;
    Label labelDefault=new Label();
    Label end =new Label();
    Label lookUpTable = new Label();
    Label start = new Label();
    //TODO keep in mind that the array list can be empty
    public Switch(Exp exp, ArrayList <Case> cases, Block blockDefault){
        this.exp = exp ;
        this.cases = cases;
        this.blockDefault = blockDefault;
    }

    public void compile2(MethodVisitor mv, ClassVisitor cv) {
       /* SymTable.getInstance().addScope(SymTable.SWITCH);
//        SymTable.getInstance().setLabelFirst(start);
//        SymTable.getInstance().setLabelLast(end);
        Label [] labels = new Label[cases.size()];
        int [] ints = new int[cases.size()];
        int i = 0 ;
//        mv.visitLabel(start);
        exp.compile(mv,cv);
        mv.visitJumpInsn(Opcodes.GOTO,lookUpTable);
        for(Case c : cases){
            c.jump = end;
            c.start = start;
            c.compile(mv,cv);
            labels[i]=c.labelStartCase;
            ints[i++]=c.exp.getValue();
        }
        mv.visitLabel(labelDefault);
        blockDefault.compile(mv,cv);
        mv.visitJumpInsn(Opcodes.GOTO,end);
        mv.visitLabel(lookUpTable);
        mv.visitLookupSwitchInsn(labelDefault,ints,labels);
        mv.visitLabel(end);
        SymTable.getInstance().popScope();*/
    }


    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        SymTable.getInstance().addScope(SymTable.SWITCH);
        SymTable.getInstance().setLabelFirst(start);
        SymTable.getInstance().setLabelLast(end);
        Label [] labels = new Label[cases.size()];
        int [] ints = new int[cases.size()];
        int i = 0 ;
        mv.visitLabel(start);
        exp.compile(mv,cv);
        mv.visitJumpInsn(Opcodes.GOTO,lookUpTable);
        for(Case c : cases){
            c.jump = end;
            c.start = start;
            c.compile(mv,cv);
            labels[i]=c.labelStartCase;
            ints[i++]=c.exp.getValue();
        }
        mv.visitLabel(labelDefault);
        blockDefault.compile(mv,cv);
        mv.visitJumpInsn(Opcodes.GOTO,end);
        mv.visitLabel(lookUpTable);
        mv.visitLookupSwitchInsn(labelDefault,ints,labels);
        mv.visitLabel(end);
        SymTable.getInstance().popScope();
    }
}
