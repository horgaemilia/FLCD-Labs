package first;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    private State state;
    private int index;
    private Deque<ParserHelper> workingStack = new LinkedList<>();
    private Deque<ParserHelper> inputStack = new LinkedList<>();
    private Grammar grammar;

    public Parser(Grammar grammar)
    {
        this.state = State.NORMAL;
        this.grammar = grammar;
        this.index = 0;
        this.inputStack.addFirst(new ParserHelper(0,grammar.getStartingSymbol(),""));
    }

   // WHEN: head of input stack is a terminal ≠ current symbol from input
    private void momentaryInsuccess()
    {
        this.state = State.BACK;
    }
    private void success()
    {
        this.state = State.FINAL;
    }

    //WHEN: head of input stack is a nonterminal
    private void expand()
    {
        // we get the first element from our input stack
        ParserHelper nonTerminal = this.inputStack.getFirst();
        //we pop the first element
        this.inputStack.removeFirst();

        //we get the first production of A
        List<String> firstProduction = grammar.getFirstProduction(nonTerminal.getElement());

        nonTerminal.setIndex(0);
        //we add the nonTerminal to the working stack
        this.workingStack.add(nonTerminal);
        //we need to add the elements of the production to the input stack
        for(int i = firstProduction.size()-1;i>=0;i--)
        {
            this.inputStack.addFirst(new ParserHelper(0,firstProduction.get(i),nonTerminal.getElement()));
        }
    }

    //WHEN: head of input stack is a terminal = current symbol from input
    private void advance()
    {
        //we need to increase the index
        this.index +=1;
        //we remove the first element from the input stack
        ParserHelper element = this.inputStack.getFirst();
        this.inputStack.removeFirst();
        //we add the element to the working stack
        this.workingStack.addFirst(element);
    }


    //WHEN: head of working stack is a terminal
    private void back()
    {
        //we need to decrease the index
        this.index -=1;
        //we remove the first element from the working stack
        ParserHelper nonTerminal = this.workingStack.getFirst();
        this.workingStack.removeFirst();
        //we add the element to the input stack
        this.inputStack.addFirst(nonTerminal);
    }


    //WHEN: head of working stack is a nonterminal
    private void anotherTry()
    {
        //we get the nonTerminal from the working stack (last element)
        ParserHelper element = this.workingStack.getLast();
        int index = element.getIndex();
        List<Production> getProductionsFromNonTerminal = grammar.getProductionsForNonTerminal(element.getElement());


        //we don't have any productions we didn't check
        if(index > getProductionsFromNonTerminal.size())
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
                    ParserHelper productionElement = this.inputStack.getFirst();
                    if(productionElement.getNonTerminal().equals(element.getElement()))
                    {
                        //we remove it
                        this.inputStack.removeFirst();
                    }
                    else
                    {
                        check = false;
                    }
                }
            }
            else
            {
                //we don't change the state
                //we remove the nonterminal from the working stack
                this.workingStack.removeLast();
                //we remove all its productions from the input stack
                boolean check = true;
                while(check)
                {
                    ParserHelper productionElement = this.inputStack.getFirst();
                    if(productionElement.getNonTerminal().equals(element.getElement()))
                    {
                        //we remove it
                        this.inputStack.removeFirst();
                    }
                    else
                    {
                        check = false;
                    }
                }
                //we need to add the nonterminal to the head of the input stack

                element.setIndex(0);
                this.inputStack.addFirst(element);
            }

        }

        //next we are in the case when we have another production
        else
        {
            this.state = State.NORMAL;
            // we change the 
        }
    }
}
