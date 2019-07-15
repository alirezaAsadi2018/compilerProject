package byteCode.statement.assignments;

import byteCode.exp.Exp;
import byteCode.exp.var.Var;
import byteCode.statement.Statement;
import jdk.internal.org.objectweb.asm.Type;

public abstract class Assignment extends Statement {
    Var var;
    Exp exp;
    Type type;
    public Assignment(Var v, Exp e) {
        this.var = v;
        this.exp = e;
    }

}
