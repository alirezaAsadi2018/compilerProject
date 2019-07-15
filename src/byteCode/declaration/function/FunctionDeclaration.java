package byteCode.declaration.function;

import byteCode.block.Block;
import byteCode.declaration.var.ArrVarDcl;
import byteCode.declaration.var.SmplVar;
import byteCode.declaration.var.VarDcl;
import byteCode.statement.Return;
import byteCode.symTable.SymTable;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

import java.util.ArrayList;

public class FunctionDeclaration extends FuncDcl {
    ArrayList<FuncArg> arguments;
    public ArrayList <Return> returns = new ArrayList<>();
    Block block;

    public FunctionDeclaration(ArrayList <FuncArg> arguments, String returnType, String name, Block block){
        super(returnType, name, arguments);
        this.arguments = arguments;
        this.block = block;
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        MethodVisitor newMv = cv.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC, name, signature,null,null);

        newMv.visitCode();
        SymTable.getInstance().addScope(SymTable.FUNCTION);
        SymTable.setLastSeenFunction(this);
        for (FuncArg f : arguments){
            if(f.getDimensions()==0){
                VarDcl v = new SmplVar(f.getName(),f.getType().getClassName(),false,false);
                v.compile(newMv,cv);
            }else{
                VarDcl v = new ArrVarDcl(f.getName(),f.getType().getClassName(),f.getDimensions(),false,false);
                v.compile(newMv,cv);
            }
        }
        block.compile(newMv,cv);

        if(returns.size()==0){
            if (type.equals(Type.VOID_TYPE)){
                newMv.visitInsn(Opcodes.RETURN);
            }else{
                throw new RuntimeException("no return statement");
            }
        }
        newMv.visitMaxs(1, 1);
        newMv.visitEnd();
        SymTable.getInstance().popScope();

    }
}
