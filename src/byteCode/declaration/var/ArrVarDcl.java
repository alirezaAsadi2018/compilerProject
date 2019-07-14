package byteCode.declaration.var;

import byteCode.exp.Exp;
import byteCode.symTable.SymTable;
import byteCode.symTable.dscp.Dscp;
import byteCode.symTable.dscp.DscpArrDynamic;
import byteCode.symTable.dscp.DscpArrStatic;
import byteCode.symTable.dscp.DscpDynamic;
import byteCode.util.UtilFunctions;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

import java.util.List;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class ArrVarDcl extends VarDcl {
    boolean Static;
    String typeString;
    private List<Exp> dimensions;

    public ArrVarDcl(String varName, String type, int dims, boolean Static, boolean Constant) {
        name = varName;
        this.Static = Static;
        this.Constant = Constant;
        typeString = type;
    }


    public ArrVarDcl(String varName, String type, List<Exp> dimensions, boolean Static, boolean Constant) {
        name = varName;
        this.dimensions = dimensions;
        this.Static = Static;
        this.Constant = Constant;
        typeString = type;
    }

    private void declare(boolean staticDec, boolean Constant) {
        Dscp dscp;

        if (staticDec) {
            dscp = new DscpArrStatic(name, getType(), dimensions.size(), Constant);
        } else {
            dscp = new DscpArrDynamic(name, getType(), SymTable.getInstance().returnNewIndex(), dimensions.size(), Constant);
        }

        SymTable.getInstance().addVariable(dscp, name);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        calculateType();
        declare(Static, Constant);
        if (getDSCP() instanceof DscpDynamic) {
            for (Exp dimension : dimensions) {
                dimension.compile(mv, cv);
            }

            if (dimensions.size() == 1) {
                if (!UtilFunctions.isObject(getType().getElementType())) {
                    mv.visitIntInsn(NEWARRAY, UtilFunctions.getTType(getType().getElementType()));
                } else {
                    mv.visitTypeInsn(ANEWARRAY, getType().getElementType().getInternalName());
                }
            } else {
                mv.visitMultiANewArrayInsn(getType().getDescriptor(), dimensions.size());
            }

            mv.visitVarInsn(ASTORE, ((DscpDynamic) getDSCP()).getIndex());
        } else {
            addFieldToClass(cv, true);
        }
    }

    @Override
    public void addFieldToClass(ClassVisitor cv, boolean isStatic) {
        int access = ACC_PUBLIC;
        access += isConstant() ? Opcodes.ACC_FINAL : 0;
        access += isStatic ? Opcodes.ACC_STATIC : 0;

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < dimensions.size(); i++) {
            stringBuilder.append("[");
        }
        Type arrayType = Type.getType(stringBuilder + type.getDescriptor());

        cv.visitField(access, getName(), arrayType.getDescriptor(), null, null).visitEnd();
    }

    @Override
    void calculateType() {
        Type varType = SymTable.getTypeFromName(typeString);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < dimensions.size(); i++) {
            stringBuilder.append("[");
        }
        type = Type.getType(stringBuilder.toString() + varType.getDescriptor());
    }
}
