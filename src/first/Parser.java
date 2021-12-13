package first;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    private State state;
    private int index;
    private Deque<ParserHelper> workingStack = new LinkedList<>();
    private Deque<ParserHelper> inputStack = new LinkedList<>();
    private Grammar grammar;
    private List<String> derivations = new ArrayList<>();

    public State getState()
    {
        return state;
    }

    public List<String> getDerivations() {
        return derivations;
    }

    public Deque<ParserHelper> getInputStack() {
        return inputStack;
    }

    public Deque<ParserHelper> getWorkingStack() {
        return workingStack;
    }

    public int getIndex()
    {
        return index;
    }
    public Parser(Grammar grammar)
    {
        this.state = State.NORMAL;
        this.grammar = grammar;
        this.index = 0;
        this.inputStack.addFirst(new ParserHelper(0,grammar.getStartingSymbol(),grammar.getStartingSymbol()));
        this.derivations.add(grammar.getStartingSymbol());
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Parser{" +
                "state=" + state +
                ", index=" + index +
                ", workingStack=" + workingStack +
                ", inputStack=" + inputStack +
                '}';
    }

    // WHEN: head of input stack is a terminal ≠ current symbol from input
    public void momentaryInsuccess()
    {
        this.state = State.BACK;
       System.out.println("momentaryInsuccess"+this.toString());
    }
    public void success()
    {
        this.state = State.FINAL;
        System.out.println("success");
    }

    //WHEN: head of input stack is a nonterminal
    public void expand()
    {
        // we get the first element from our input stack
        ParserHelper nonTerminal = this.inputStack.getFirst();
        //we pop the first element
        this.inputStack.removeFirst();

        //we get the first production of A
        List<String> firstProduction = grammar.getFirstProduction(nonTerminal.getElement());

        nonTerminal.setIndex(0);
        //we add the nonTerminal to the working stack
        this.workingStack.addLast(nonTerminal);
        //we need to add the elements of the production to the input stack
        for(int i = firstProduction.size()-1;i>=0;i--)
        {
            this.inputStack.addFirst(new ParserHelper(0,firstProduction.get(i),nonTerminal.getElement()));
        }
        System.out.println("expand"+this.toString());
        //now we compute the derivation string
        String previousElement = derivations.get(derivations.size()-1);
        String production = previousElement.replaceFirst(nonTerminal.getElement(),grammar.getFirstProductionAsString(nonTerminal.getElement()));
        derivations.add(production);
    }

    //WHEN: head of input stack is a terminal = current symbol from input
    public void advance()
    {
       // System.out.println("advance"+this.toString());
        //we need to increase the index
        this.index +=this.inputStack.getFirst().getElement().length();
        //we remove the first element from the input stack
        ParserHelper element = this.inputStack.getFirst();
        this.inputStack.removeFirst();
        //we add the element to the working stack
        this.workingStack.addLast(element);
        System.out.println("advance"+this.toString());
    }


    //WHEN: head of working stack is a terminal
    public void back()
    {
        this.state = State.BACK;
        //we need to decrease the index
        this.index -=this.workingStack.getLast().getElement().length();
        //we remove the last element from the working stack
        ParserHelper terminal = this.workingStack.getLast();
        this.workingStack.removeLast();
        //we add the element to the input stack
        this.inputStack.addFirst(terminal);
        System.out.println("back"+this.toString());
    }


    //WHEN: head of working stack is a nonterminal
    public void anotherTry()
    {
        //we get the nonTerminal from the working stack (last element)
        ParserHelper element = this.workingStack.getLast();
        int index = element.getIndex()+1;
        List<Production> getProductionsFromNonTerminal = grammar.getProductionsForNonTerminal(element.getElement());
        //we don't have any productions we didn't check
        if(index >= getProductionsFromNonTerminal.size())
        {
            if(this.index==0 && element.getElement().equals(grammar.getStartingSymbol()))
            {
                //we have an error
                this.state = State.ERROR;
                //we remove the non terminal from the working stack
                this.workingStack.removeLast();
                //we remove all the corresponding productions from the input stack
                boolean check = true;
                while(check)
                {
                    if(this.inputStack.isEmpty())
                        check = false;
                    else {
                        ParserHelper productionElement = this.inputStack.getFirst();
                        if (productionElement.getNonTerminal().equals(element.getElement()) && productionElement.getIndex() == element.getIndex()) {
                            //we remove it
                            this.inputStack.removeFirst();
                        } else {
                            check = false;
                        }
                    }
                }
            }
            else
            {
                //we don't change the state
                //we remove the nonterminal from the working stack
                this.state = State.BACK;
                this.workingStack.removeLast();
                //we need to delete the last derivation
                derivations.remove(derivations.size()-1);
                //we remove all its productions from the input stack
                boolean check = true;
                while(check)
                {
                    if(this.inputStack.isEmpty())
                        check = false;
                    else {
                        ParserHelper productionElement = this.inputStack.getFirst();
                        if (productionElement.getNonTerminal().equals(element.getElement())&&productionElement.getIndex() == element.getIndex()) {
                            //we remove it
                            this.inputStack.removeFirst();
                        } else {
                            check = false;
                        }
                    }
                }
                //we need to add the nonterminal to the head of the input stack
                this.inputStack.addFirst(element);
            }
        }

        //next we are in the case when we have another production
        else
        {
            // we change the state to normal
            this.state = State.NORMAL;
            //the non terminal
            ParserHelper productionElement = this.workingStack.removeLast();
            productionElement.setIndex(index);
            this.workingStack.addLast(productionElement);
            boolean check = true;
            while(check)
            {
                if(this.inputStack.isEmpty())
                    check = false;
                else {
                    ParserHelper inputElement = this.inputStack.getFirst();
                    if (inputElement.getNonTerminal().equals(element.getElement()) && inputElement.getIndex() == (index-1)) {
                        //we remove it
                        this.inputStack.removeFirst();
                    } else {
                        check = false;
                    }
                }
            }
            Production production = getProductionsFromNonTerminal.get(index);
            //we need to add the elements of the production to the input stack
            for(int i = production.getOrderedSymbols().size()-1;i>=0;i--)
            {
                this.inputStack.addFirst(new ParserHelper(index,production.getOrderedSymbols().get(i),productionElement.getElement()));
            }
            derivations.remove(derivations.size()-1);
            String productionString = production.getOrderedSymbolsAsAString();
            String previousElement = derivations.get(derivations.size()-1);
            String newProduction = previousElement.replaceFirst(element.getElement(),productionString);
            derivations.add(newProduction);
        }
        System.out.println("anotherTry"+this.toString());
    }
}
