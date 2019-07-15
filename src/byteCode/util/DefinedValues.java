package byteCode.util;

import java.nio.file.Paths;

public class DefinedValues {
    public static String nameClass = "$Main";
    public static String compilePath = Paths.get("compiled/").toAbsolutePath().toString();
}
