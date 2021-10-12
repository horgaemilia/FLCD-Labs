package first;

import hashTable.HashTable;

import java.io.Console;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        HashTable symbolTable = new HashTable();
        System.out.println(symbolTable.getPosition("a"));
        System.out.println(symbolTable.getPosition("123"));
        System.out.println(symbolTable.getPosition("ad"));
        System.out.println(symbolTable.getPosition("1234"));
	// write your code here
    }
}
