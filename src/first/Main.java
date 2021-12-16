package first;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static void printMenu() {
        System.out.println("Option 0: exit");
        System.out.println("Option 1: non terminals");
        System.out.println("Option 2: terminals");
        System.out.println("Option 3: starting symbol");
        System.out.println("Option 4: productions");
        System.out.println("Option 5: productions for a given nonterminal");
        System.out.println("Option 6: check for cfg");

    }

    private static List<String> convertFileToList(String filename)
    {
        List<String> list = new ArrayList<>();
        File program = new File(filename);
        try {
            Scanner reader = new Scanner(program);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] lineSplitBySpace = line.split(" ");
                String targetElement = lineSplitBySpace[0];
                list.add(targetElement);
            }
            reader.close();
        }
        catch (Exception ignored){
            ignored.printStackTrace();
        }
        return list;
    }

    private static void getProductionsForNonterminal(Grammar grammar) {
        System.out.println("Please enter your symbol");
        Scanner in = new Scanner(System.in);
        String nonTerminal = in.nextLine();
        List<Production> productions = grammar.getProductionsForNonTerminal(nonTerminal);
        System.out.println("Productions for: " + nonTerminal + " are:");
        productions.forEach(p -> System.out.println(p.toString()));
    }

    public static void main(String[] args) {
        Grammar grammar = new Grammar("src/G3.txt");
        ParserOutput parserOutput = new ParserOutput("src/G1.txt","src/out1.txt");
        String filename = "src/seq.txt";
        List<String> sequence = convertFileToList(filename);
        System.out.println(sequence);
        System.out.println(parserOutput.checkSequence(sequence));

        ParserOutput parserOutput2 = new ParserOutput("src/G3.txt","src/out2.txt");
        String filename2 = "src/pif.txt";
        List<String> sequence2 = convertFileToList(filename2);
        System.out.println(sequence2);
        System.out.println(parserOutput2.checkSequence(sequence2));
//        int option = 6;
//        boolean notFinished = true;
//        while (notFinished) {
//            printMenu();
//            option = in.nextInt();
//            switch (option) {
//                case 0 -> {
//                    notFinished = false;
//                }
//                case 1 -> {
//                    var nonterminalSymbols = grammar.getNonterminalSymbols();
//                    System.out.println(nonterminalSymbols);
//                }
//                case 2 -> {
//                    var terminalSymbols = grammar.getTerminalSymbols();
//                    System.out.println(terminalSymbols);
//                }
//                case 3 -> {
//                    var startingSymbol = grammar.getStartingSymbol();
//                    System.out.println(startingSymbol);
//                }
//                case 4 -> {
//                    var productions = grammar.getProductions();
//                    productions.forEach(p -> System.out.println(p.toString()));
//                }
//                case 5 -> {
//                    getProductionsForNonterminal(grammar);
//                }
//                case 6 -> {
//                    System.out.println(grammar.isContextFree() ? "Is context-free" : "Is NOT context-free");
//                }
//            }
//        }
    }
}
