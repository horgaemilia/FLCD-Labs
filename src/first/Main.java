package first;

import errors.LexicalError;
import hashTable.SymbolTable;
import scanner.ProgramScanner;

public class Main {

    public static void main(String[] args) {

       /* SymbolTable symbolTable = new SymbolTable();
        System.out.println(symbolTable.getPosition("a"));
        System.out.println(symbolTable.getPosition("123"));
        System.out.println(symbolTable.getPosition("ad"));
        System.out.println(symbolTable.getPosition("1234"));
        System.out.println(symbolTable.getPosition("a"));
        symbolTable.writeToFile();*/
        ProgramScanner scanner = new ProgramScanner("p1err.in");
        try
        {
            scanner.scan();
        }
        catch (LexicalError error)
        {
            System.out.println(error.getMessage());
        }
    }
}
