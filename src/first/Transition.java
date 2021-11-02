package first;

public class Transition {
    private String from;
    private String alphabetElement;
    private String to;


    public Transition(String from, String alphabetElement, String to) {
        this.from = from;
        this.alphabetElement = alphabetElement;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAlphabetElement() {
        return alphabetElement;
    }

    public void setAlphabetElement(String alphabetElement) {
        this.alphabetElement = alphabetElement;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return this.from + "->" + this.alphabetElement + "->" + this.to;
    }
}
