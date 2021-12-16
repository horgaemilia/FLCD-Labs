package first;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class ParserOutput {
    private Grammar grammar;
    private Parser parser;
    private String outputFile;

    public ParserOutput(String grammarFile,String outputFile) {
        this.grammar = new Grammar(grammarFile);
        this.parser = new Parser(this.grammar);
        this.outputFile = outputFile;
    }


    public void writeDerivationToFile()
    {
        List<String> derivation = this.parser.getDerivations();
        StringBuilder stringBuilder = new StringBuilder();
        derivation.forEach(d->stringBuilder.append("[").append(d).append("]->"));
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(stringBuilder.toString());
            writer.close();
        }
        catch (Exception ignored){}
    }

    public void writeErrorToFile()
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write("not accepted");
            writer.close();
        }
        catch (Exception ignored){}
    }

    public boolean checkSequence(List<String> sequence) {
        while ((!this.parser.getState().equals(State.FINAL)) && (!this.parser.getState().equals(State.ERROR))) {
            if (this.parser.getState().equals(State.NORMAL)) {
                if (this.parser.getIndex() == sequence.size() && this.parser.getInputStack().isEmpty())
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
                            if (sequence.size() <= this.parser.getIndex())
                            {   this.parser.momentaryInsuccess();
                                //System.out.println(parser.getDerivations());
                            }
                            else {
                                String elementAtIndex = sequence.get(this.parser.getIndex());
                                if (headInputStack.getElement().equals(elementAtIndex)) {
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
            writeErrorToFile();
            return false;
        }
        System.out.println(parser.getDerivations());
        writeDerivationToFile();
        return true;
    }
}
