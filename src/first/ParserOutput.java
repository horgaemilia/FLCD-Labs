package first;

import java.util.List;

public class ParserOutput {
    private Grammar grammar;
    private Parser parser;

    public ParserOutput(String grammarFile) {
        this.grammar = new Grammar(grammarFile);
        this.parser = new Parser(this.grammar);
    }

    public boolean checkSequence(String sequence) {
        while ((!this.parser.getState().equals(State.FINAL)) && (!this.parser.getState().equals(State.ERROR))) {
            if (this.parser.getState().equals(State.NORMAL)) {
                if (this.parser.getIndex() == sequence.length() && this.parser.getInputStack().isEmpty())
                    this.parser.success();
                else {
                    if (this.parser.getInputStack().isEmpty())
                        this.parser.momentaryInsuccess();
                    else {
                        ParserHelper headInputStack = this.parser.getInputStack().getFirst();
                        if (this.grammar.getNonterminalSymbols().contains(headInputStack.getElement())) {
                            this.parser.expand();
                        } else {
                            if (sequence.length() <= this.parser.getIndex())
                                this.parser.momentaryInsuccess();
                            else {
                                char elementAtIndex = sequence.charAt(this.parser.getIndex());
                                if (headInputStack.getElement().equals(Character.toString(elementAtIndex))) {
                                    this.parser.advance();
                                } else
                                    this.parser.momentaryInsuccess();
                            }
                        }
                    }
                }
            } else {
                if (this.parser.getState().equals(State.BACK)) {
                    ParserHelper headWorkingStack = this.parser.getWorkingStack().getLast();
                    if (this.grammar.getTerminalSymbols().contains(headWorkingStack.getElement())) {
                        this.parser.back();
                    } else {
                        this.parser.anotherTry();
                    }
                }
            }
        }
        if (this.parser.getState().equals(State.ERROR))
        {
            System.out.println(parser.getDerivations());
            return false;
        }
        System.out.println(parser.getDerivations());
        return true;
    }
}
