package first;

public class ParserHelper {
    private int index;
    private String element;
    private String nonTerminal;

    public ParserHelper(int index,String element,String nonTerminal) {
        this.index = index;
        this.element = element;
        this.nonTerminal = nonTerminal;
    }

    @Override
    public String toString() {
        return "ParserHelper{" +
                "index=" + index +
                ", element='" + element + '\'' +
                ", nonTerminal='" + nonTerminal + '\'' +
                '}';
    }

    public void setIndex(int index)
    {
        this.index = index;
    }
    public int getIndex() {
        return index;
    }

    public String getNonTerminal()
    {
        return this.nonTerminal;
    }

    public String getElement()
    {
        return element;
    }
}
