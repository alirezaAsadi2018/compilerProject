package myJava;

import byteCode.Program;
import byteCode.util.DefinedValues;
import cup.parser;
import java_cup.runtime.ComplexSymbolFactory;
import jflex.MyScanner;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class MyMain {
    public static void main(String[] args) {
        ComplexSymbolFactory csf = new ComplexSymbolFactory();
        try {
            MyScanner scanner = new MyScanner(new FileReader("src/input.c"), csf);

//            ArrayList<StaticVarsExtern> ins = new ArrayList<>();
//            ins.add(new StaticVarsExtern("Ljava/io/PrintStream;","out","java/lang/System"));
//            ExternFuncDcl a = new ExternFuncDcl("java/io/PrintStream",ins,"println","(I)V");
//            a = new ExternFuncDcl("java/io/PrintStream",ins,"println","(D)V");
//            ins = new ArrayList<>();
//            ins.add(new StaticVarsExtern("Ljava/io/PrintStream;","out","java/lang/System"));

            parser p = new parser(scanner, csf);
            Object result = p.parse().value;
            Program pr = (Program) result;
            pr.compile(DefinedValues.nameClass);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("parsing failed");
        }
    }
}
