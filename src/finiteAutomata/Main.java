package finiteAutomata;

import java.util.Scanner;

public class Main {

    private static void printMenu()
    {
        System.out.println("Option 0: exit");
        System.out.println("Option 1: states");
        System.out.println("Option 2: alphabet");
        System.out.println("Option 3: initial state");
        System.out.println("Option 4: final states");
        System.out.println("Option 5: transitions");
        System.out.println("Option 6: check sequence");

    }

    private static void checkSequence(FA finiteAutomaton)
    {
        System.out.println("Please enter your sequence");
        Scanner in = new Scanner(System.in);
        String sequence = in.nextLine();
        boolean result = finiteAutomaton.checkSequence(sequence);
        if (result) {
            System.out.println("the sequence can be obtained");
        } else
            {
            System.out.println("the sequence can not be obtained");
        }
    }

    public static void main(String[] args) {
	// write your code here
        FA finiteAutomaton = new FA("src/FA.in");
        //System.out.println(finiteAutomaton.toString());
        Scanner in = new Scanner(System.in);
        int option = 6;
        boolean notFinished = true;
        while (notFinished)
        {
            printMenu();
            option = in.nextInt();
            switch (option)
            {
                case 0 -> {
                    notFinished=false;
                }
                case 1 -> {
                    var states = finiteAutomaton.getStates();
                    System.out.println(states);
                }
                case 2 ->{
                    var alphabet = finiteAutomaton.getAlphabet();
                    System.out.println(alphabet);
                }
                case 3 ->{
                    var initialState = finiteAutomaton.getInitialState();
                    System.out.println(initialState);
                }
                case 4 ->{
                    var finalStates = finiteAutomaton.getFinalStates();
                    System.out.println(finalStates);
                }
                case 5 ->{
                    var transitions = finiteAutomaton.getTransitions();
                    System.out.println(transitions);
                }
                case 6 ->{
                    checkSequence(finiteAutomaton);
                }
            }
        }
    }
}
