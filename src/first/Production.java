package first;

import java.util.ArrayList;
import java.util.List;

public class Production {
    private List<String> left;
    private List<String> orderedSymbols;

    public Production()
    {
        left = new ArrayList<>();
        orderedSymbols = new ArrayList<>();
    }

    public void addElement(String element)
    {
        this.orderedSymbols.add(element);
    }

    public void setOrderedSymbols(List<String> orderedSymbols) {
        this.orderedSymbols = orderedSymbols;
    }

    public List<String> getOrderedSymbols() {
        return orderedSymbols;
    }

    public List<String> getLeft() {
        return left;
    }

    public void setLeft(List<String> right) {
        this.left = right;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.left.forEach(stringBuilder::append);
        stringBuilder.append("->");
        this.orderedSymbols.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
