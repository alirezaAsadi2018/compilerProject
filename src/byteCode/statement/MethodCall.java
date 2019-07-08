package byteCode.statement;

import byteCode.exp.Exp;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;

public class MethodCall extends Statement {
    public MethodCall(String id, ArrayList<Exp> exps) {

    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {

    }
}
