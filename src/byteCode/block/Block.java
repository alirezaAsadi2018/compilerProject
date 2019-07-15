package byteCode.block;

import byteCode.Node;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;

public class Block extends Node {
    ArrayList <Node> nodes;
    public Block(ArrayList <Node> operationCodes){
        this.nodes = operationCodes;
    }
    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        for(Node node : nodes){
            node.compile(mv,cv);
        }
    }
}
