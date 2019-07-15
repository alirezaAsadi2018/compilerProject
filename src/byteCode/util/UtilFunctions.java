package byteCode.util;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

public class UtilFunctions {

    public static boolean isObject(Type type) {
        return type != Type.BOOLEAN_TYPE && type != Type.INT_TYPE && type != Type.CHAR_TYPE && type != Type.LONG_TYPE && type != Type.FLOAT_TYPE && type != Type.DOUBLE_TYPE;
    }

    public static boolean isInteger(Type type) {
        return type == Type.BOOLEAN_TYPE || type == Type.INT_TYPE || type == Type.CHAR_TYPE;
    }

    private static boolean checkForDouble(Type type1, Type type2, MethodVisitor mv, ClassVisitor cv) {
        if (type2 == Type.DOUBLE_TYPE) {
            if (type1 == Type.FLOAT_TYPE) {
                mv.visitInsn(Opcodes.F2D);
            } else if (isInteger(type1)) {
                mv.visitInsn(Opcodes.I2D);
            } else if (type1 == Type.LONG_TYPE) {
                mv.visitInsn(Opcodes.L2D);
            }
            return true;
        }
        return false;
    }

    private static boolean checkForFloat(Type type1, Type type2, MethodVisitor mv, ClassVisitor cv) {
        if (type2 == Type.FLOAT_TYPE) {
            if (isInteger(type1)) {
                mv.visitInsn(Opcodes.I2F);
            } else if (type1 == Type.LONG_TYPE) {
                mv.visitInsn(Opcodes.L2F);
            }
            return true;
        }
        return false;
    }

    private static boolean checkForLong(Type type1, Type type2, MethodVisitor mv, ClassVisitor cv) {
        if (type2 == Type.LONG_TYPE) {
            if (isInteger(type1)) {
                mv.visitInsn(Opcodes.I2L);
            }
            return true;
        }
        return false;
    }

    public static void cast(Type type1, Type type2, MethodVisitor mv, ClassVisitor cv) {

        if (!checkForDouble(type1, type2, mv, cv)) {
            if (!checkForFloat(type1, type2, mv, cv)) {
                checkForLong(type1, type2, mv, cv);
            }
        }

    }

    public static int getTType(Type type) {
        if (type == Type.INT_TYPE)
            return Opcodes.T_INT;
        else if (type == Type.LONG_TYPE)
            return Opcodes.T_LONG;
        else if (type == Type.DOUBLE_TYPE)
            return Opcodes.T_DOUBLE;
        else if (type == Type.CHAR_TYPE)
            return Opcodes.T_CHAR;
        else //(type == Type.BOOLEAN_TYPE)
            return Opcodes.T_BOOLEAN;
    }
}
