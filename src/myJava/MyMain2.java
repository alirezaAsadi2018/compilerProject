package myJava;

import byteCode.declaration.function.ExternFuncDcl;
import byteCode.declaration.function.StaticVarsExtern;
import byteCode.exp.Exp;
import byteCode.exp.constant.IntConstExp;
import byteCode.statement.MethodCall;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class MyMain2 {
    public static void main(String[] args) throws Exception {

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        TraceClassVisitor cv = new TraceClassVisitor(cw, new PrintWriter(Paths.get("./CodeOut.txt").toFile()));

        cv.visit(V1_8, ACC_PUBLIC + ACC_SUPER, "$Main", null, Type.getInternalName(Object.class), null);
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();


        ArrayList<StaticVarsExtern> ins = new ArrayList<StaticVarsExtern>();
        ins.add(new StaticVarsExtern("Ljava/io/PrintStream;","out","java/lang/System"));
        ExternFuncDcl a = new ExternFuncDcl("java/io/PrintStream",ins,"println","(I)V");
        ArrayList <Exp> exps = new ArrayList<Exp>();
        exps.add(new IntConstExp(10));
        MethodCall methodCall = new MethodCall("println",exps);
        methodCall.compile(mv,cv);


        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
        cv.visitEnd();
        byte[] bytes = cw.toByteArray();
        MainClassLoader loader = new MainClassLoader();
        Class<?> clazz = loader.defineClass("$Main", bytes);
        Method main = clazz.getMethod("main", String[].class);
        String[] arguments = new String[0];
        main.invoke(null, (Object) arguments);

    }
}
class MainClassLoader extends ClassLoader {
    public Class defineClass(String name, byte[] b) {
        return defineClass(name, b, 0, b.length);
    }
}
