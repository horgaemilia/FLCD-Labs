package finiteAutomata;

public class FaHelper {
    String obtainedSequence;
    String state;

    public FaHelper(String obtainedSequence,String state)
    {
        this.obtainedSequence = obtainedSequence;
        this.state = state;
    }

    @Override
    public String toString() {
        return "FaHelper{" +
                "obtainedSequence='" + obtainedSequence + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
