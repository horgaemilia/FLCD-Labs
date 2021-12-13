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
        System.out.println(sequence.length());
        while ((!this.parser.getState().equals(State.FINAL)) && (!this.parser.getState().equals(State.ERROR))) {
            if (this.parser.getState().equals(State.NORMAL)) {
                if (this.parser.getIndex() == sequence.length() && this.parser.getInputStack().isEmpty())
                {
                    this.parser.success();
                    //System.out.println(parser.getDerivations() );
                }
                else {
                    if (this.parser.getInputStack().isEmpty())
                    {   this.parser.momentaryInsuccess();
                       // System.out.println(parser.getDerivations());
                    }
                    else {
                        ParserHelper headInputStack = this.parser.getInputStack().getFirst();
                        if (this.grammar.getNonterminalSymbols().contains(headInputStack.getElement())) {
                            {
                                this.parser.expand();
                                //System.out.println(parser.getDerivations());
                            }
                        } else {
                            if (sequence.length() <= this.parser.getIndex())
                            {   this.parser.momentaryInsuccess();
                                //System.out.println(parser.getDerivations());
                            }
                            else {
                                char elementAtIndex = sequence.charAt(this.parser.getIndex());
                                int headInputStackLength = headInputStack.getElement().length();
                                StringBuilder stringBuilder = new StringBuilder();
                                if(this.parser.getIndex()+headInputStackLength <= sequence.length())
                                {
                                    for(int i=0;i<headInputStackLength;i++)
                                        stringBuilder.append(sequence.charAt(this.parser.getIndex()+i));
                                }
                                if (headInputStack.getElement().equals(stringBuilder.toString())) {
                                    { this.parser.advance();
                                       // System.out.println(parser.getDerivations());
                                    }
                                } else
                                {this.parser.momentaryInsuccess();
                                   // System.out.println(parser.getDerivations());
                                }
                            }
                        }
                    }
                }
            } else {
                if (this.parser.getState().equals(State.BACK)) {
                    ParserHelper headWorkingStack = this.parser.getWorkingStack().getLast();
                    if (this.grammar.getTerminalSymbols().contains(headWorkingStack.getElement())) {
                        {this.parser.back();
                           // System.out.println(parser.getDerivations());
                        }
                    } else {
                        {this.parser.anotherTry();
                           // System.out.println(parser.getDerivations());
                        }
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
