package byteCode.declaration.var;

import byteCode.Node;
import byteCode.symTable.SymTable;
import byteCode.util.DefinedValues;
import byteCode.util.UtilFunctions;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class StructDcl extends Node {
    private HashMap<String, VarDcl> variables = new HashMap<>();
    private String name;
    private Type type;

    public StructDcl(ArrayList<Dcls> fields, String name) {
        this.name = name;

        for (Dcls dcls : fields) {
            ArrayList<VarDcl> vars = dcls.getVariables();
            for (VarDcl var : vars) {
                variables.put(var.getName(), var);
            }
        }
        type = Type.getType("L" + name + ";");
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }


    private void declare() {
        SymTable.getInstance().addRecord(this);
    }

    @Override
    public void compile(MethodVisitor MV, ClassVisitor CV) {
        declare();
        ClassWriter cw;
        TraceClassVisitor cv;



        cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        try {
            cv = new TraceClassVisitor(cw, new PrintWriter(Paths.get(DefinedValues.compilePath, getName() + ".txt").toFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        cv.visit(V1_8, ACC_PUBLIC + ACC_SUPER, getName(), null, Type.getInternalName(Object.class), null);
        for (VarDcl var : variables.values()) {
            var.calculateType();
            if (UtilFunctions.isObject(var.getType()) && !SymTable.getInstance().isRecordDefined(var.getType().getClassName())) {
//                throw new InvalidVariableType("Record Type Is Not Defined");
            }
            var.addFieldToClass(cv, false);
        }


        MethodVisitor mv; mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        cv.visitEnd();
        byte[] bytes = cw.toByteArray();
        try {
            Files.write(Paths.get(DefinedValues.compilePath,  getName() + ".class"), bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
