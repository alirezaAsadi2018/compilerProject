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
            MyScanner scanner = new MyScanner(new FileReader("input - Copy.c"), csf);
            parser p = new parser(scanner, csf);
            Object result = p.parse().value;
            Program program = (Program) result;
            program.compile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("fail");
        }
    }
}
