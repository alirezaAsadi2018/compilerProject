package byteCode.statement.loop;

import byteCode.block.Block;
import byteCode.statement.Statement;

public abstract class Loop extends Statement {
    Block block;
    Loop(Block block){
        this.block = block;
    }
}
