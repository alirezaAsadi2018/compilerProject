package byteCode.statement;

import byteCode.declaration.function.FunctionDeclaration;
import byteCode.exp.Exp;
import byteCode.symTable.SymTable;
import byteCode.util.UtilFunctions;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

import java.util.HashMap;

public class Return extends Statement{
    Exp exp1;
    HashMap scope ;
    public Return(){
        exp1=null;
    }
    public Return(Exp exp1){
        this.exp1 = exp1;
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        scope = SymTable.getInstance().getLastFrame();
        FunctionDeclaration f = (FunctionDeclaration)SymTable.getLastSeenFunction();
        for(Return r : f.returns){
            if(r.scope==scope){
                throw new RuntimeException("more than one return in one scope");
            }
        }
        f.returns.add(this);
        if(exp1!=null){
            exp1.compile(mv,cv);
            if(f.getType().equals(Type.VOID_TYPE)){
                throw new RuntimeException("you can not return in void");
            }
            if(!f.getType().equals(exp1.getType())){
                throw new RuntimeException("type mismatch");
            }
            UtilFunctions.cast(exp1.getType(),f.getType(),mv,cv);
            mv.visitInsn(exp1.getType().getOpcode(Opcodes.IRETURN));
        }else {
            if(!f.getType().equals(Type.VOID_TYPE)){
                throw new RuntimeException("should  return something");
            }
            mv.visitInsn(Opcodes.RETURN);
        }
    }
}
