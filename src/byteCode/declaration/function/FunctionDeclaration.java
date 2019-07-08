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
    public ArrayList <Return> returns = new ArrayList<Return>();
    Block block;
    /**
     *
     * @param arguments contains arguments of String type and also an integer for dims
     * @param type  the type that the function returns
     */
    public FunctionDeclaration(ArrayList <FuncArg> arguments, String type, String name, Block block){
        super(type, name, arguments);
        //TODO keep in mind that the arguments and the block part may be null
        this.arguments = arguments;
        this.block = block;
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
//        TODO we can add generics and others later (that's why the last two are null)
        MethodVisitor newMv = cv.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC,name,this.signature,null,null);

        newMv.visitCode();
//        System.out.println(SymTable.getInstance().getLastFrame().keySet());
        SymTable.getInstance().addScope(SymTable.FUNCTION);
//        System.out.println(SymTable.getInstance().getLastFrame().keySet());

        SymTable.setLastSeenFunction(this);

//        this part shall be for declaring new variables

        for (FuncArg f : arguments){
            if(f.getDimensions()==0){
//                   TODO we can add constant to the function too
                VarDcl v = new SmplVar(f.getName(),f.getType().getClassName(),false,false);
                v.compile(newMv,cv);
            }else{
//                   TODO we can add constant to the function too
                VarDcl v = new ArrVarDcl(f.getName(),f.getType().getClassName(),f.getDimensions(),false,false);
                v.compile(newMv,cv);
            }
        }


//
        block.compile(newMv,cv);

        if(returns.size()==0){
            if (type.equals(Type.VOID_TYPE)){
                newMv.visitInsn(Opcodes.RETURN);
            }else{
                throw new RuntimeException("no return type seen , but should have seen one");
            }
        }
        newMv.visitMaxs(1, 1);
        newMv.visitEnd();
        SymTable.getInstance().popScope();

    }
}
