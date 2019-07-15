package byteCode;

import byteCode.declaration.function.FuncDcl;
import byteCode.declaration.var.Dcls;
import byteCode.declaration.var.StructDcl;
import byteCode.symTable.SymTable;
import byteCode.util.DefinedValues;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class Program {
    public ArrayList<Node> nodes = new ArrayList<>();

    public Program() {

    }

    public Program add(Node e) {
        nodes.add(e);
        return this;
    }

    public void compile() throws FileNotFoundException {


        ClassWriter cw;
        TraceClassVisitor cv;
        MethodVisitor mv;
        cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cv = new TraceClassVisitor(cw, new PrintWriter(Paths.get(DefinedValues.compilePath, DefinedValues.nameClass+".txt").toFile()));

        cv.visit(V1_8, ACC_PUBLIC + ACC_SUPER, DefinedValues.nameClass, null, Type.getInternalName(Object.class), null);
        mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        for (Node c : nodes) {
            if (c instanceof StructDcl) {
                c.compile(mv, cv);
            }else if (c instanceof Dcls) {
                c.compile(mv, cv);
            }else if(c instanceof FuncDcl) {
                c.compile(mv, cv);
            }
        }

        mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();

        FuncDcl f = SymTable.getInstance().getFunction("main", new Type[0]);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, DefinedValues.nameClass, f.getName(), f.getSignature(), false);
        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
        cv.visitEnd();
        byte[] bytes = cw.toByteArray();

        try {
            Files.write(Paths.get(DefinedValues.compilePath, DefinedValues.nameClass+".class"), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
