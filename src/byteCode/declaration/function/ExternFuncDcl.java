package byteCode.declaration.function;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;

public class ExternFuncDcl extends FuncDcl{

    public ExternFuncDcl(String returnType, String name, ArrayList<FuncArg> arguments) {
        super(returnType, name, arguments);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {

    }
}
