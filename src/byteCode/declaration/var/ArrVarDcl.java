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
    String type1;
    private List<Exp> dimensions;

    public ArrVarDcl(String varName, String type, int dims, boolean Static, boolean Constant) {
        name = varName;
        this.Static = Static;
        this.Constant = Constant;
        type1 = type;
        //TODO do something with constant
    }


    public ArrVarDcl(String varName, String type, List<Exp> dimensions, boolean Static, boolean Constant) {
        name = varName;
        this.dimensions = dimensions;
        this.Static = Static;
        this.Constant = Constant;
        type1 = type;
        //TODO do something with constant
    }

    private void declare(boolean staticDec, Type varType, boolean Constant) {
        // TODO: 01/07/2018 SymbolTable Should Change
        if (name == null || varType == null)
            throw new IllegalArgumentException();


        dimensions.forEach(exp -> {
            if (!UtilFunctions.isInteger(exp.getType()))
                throw new RuntimeException("Bad Index Type"); // TODO: 01/07/2018 Write Good Exception
        });

        calculateType();
        Dscp dscp;

        if (staticDec) {
            dscp = new DscpArrStatic(name, type, dimensions.size(), Constant);
        } else {
            dscp = new DscpArrDynamic(name, type, SymTable.getInstance().returnNewIndex(), dimensions.size(), Constant);
        }

        SymTable.getInstance().addVariable(dscp, name);
    }

    @Override
    public void compile(MethodVisitor mv, ClassVisitor cv) {
        calculateType();
        declare(Static, type, Constant);
        if (getDSCP() instanceof DscpDynamic) {
            for (Exp dimension : dimensions) {
                dimension.compile(mv, cv);
            }

            if (dimensions.size() == 1) {
                if (!UtilFunctions.isRecord(getType().getElementType())) {
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

        String repeatedArray = new String(new char[dimensions.size()]).replace("\0", "[");
        Type arrayType = Type.getType(repeatedArray + type.getDescriptor());

        cv.visitField(access, getName(), arrayType.getDescriptor(), null, null).visitEnd();
    }

    @Override
    void calculateType() {
        // TODO: 02/07/2018 Check is Defined For Records
        Type varType = SymTable.getTypeFromName(type1);
        String repeatedArray = new String(new char[dimensions.size()]).replace("\0", "[");
        type = Type.getType(repeatedArray + varType.getDescriptor());
    }
}
