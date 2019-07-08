package byteCode.block;

import byteCode.Node;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;

public class Block extends Node {
    ArrayList <Node> operationCodes = new ArrayList<>();
    public Block(ArrayList <Node> operationCodes){
        this.operationCodes = operationCodes;
    }
    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        for(Node oc : operationCodes){
            oc.compile(mv,cv);
        }
    }
}
