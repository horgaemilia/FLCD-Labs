package first;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FA {
    private List<String> states;
    private List<String> alphabet;
    private String initialState;
    private List<String> finalStates;
    private List<Transition> transitions;
    private String filename;


    public FA(String filename) {
        this.states = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.initialState = "";
        this.finalStates = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.filename = filename;
        readFaElementsFromFile();
    }

    @Override
    public String toString()
    {
        return "FA{" +
                "states=" + states +"\n"+
                "alphabet=" + alphabet +"\n"+
                "initialState=" + initialState +"\n"+
                "finalStates=" + finalStates +"\n"+
                "transitions=" + transitions +
                '}';
    }


    public boolean checkSequence(String sequence)
    {
        Deque<FaHelper> queue = new LinkedList<>();
        queue.add(new FaHelper("",initialState));
        while(!queue.isEmpty())
        {
            FaHelper current = queue.getFirst();
            queue.removeFirst();
            //System.out.println(current);
            String state = current.state;
            if(current.obtainedSequence.equals(sequence) && this.finalStates.contains(current.state))
                return true;
            for(var tran: this.transitions)
            {
                if(tran.getFrom().equals(state))
                {
                    //System.out.println(tran.getFrom());
                    String obtainedSequence = current.obtainedSequence + tran.getAlphabetElement();
                    FaHelper newElement = new FaHelper(obtainedSequence,tran.getTo());
                    if(sequence.startsWith(newElement.obtainedSequence))
                    {
                        //System.out.println(newElement.obtainedSequence);
                        queue.addFirst(newElement);
                    }
                }
            }
        }
        return false;
    }

    private void readFaElementsFromFile()
    {
        String mode = "";
        File program = new File(filename);
        try {
            Scanner reader = new Scanner(program);
            while (reader.hasNextLine())
            {
                String line = reader.nextLine();
                if(line.contains("States"))
                {
                    mode = "States";
                    continue;
                }
                if(line.contains("Alphabet"))
                {
                    mode = "Alphabet";
                    continue;
                }
                if(line.contains("InitialState"))
                {
                    mode = "InitialState";
                    continue;
                }
                if(line.contains("FinalState"))
                {
                    mode = "FinalState";
                    continue;
                }
                if(line.contains("Transitions"))
                {
                    mode = "Transitions";
                    continue;
                }
                switch (mode) {
                    case "States" -> {
                        this.states.add(line);
                    }
                    case "Alphabet" -> {
                        this.alphabet.add(line);
                    }
                    case "InitialState" -> {
                        this.initialState = line;
                    }
                    case "FinalState" -> {
                        this.finalStates.add(line);
                    }
                    case "Transitions" -> {
                        String[] transitionElements = line.split("->");
                        if (transitionElements.length == 3) {
                            String from = transitionElements[0];
                            from = from.replaceAll("\\s+","");
                            String alphabetElement = transitionElements[1];
                            alphabetElement = alphabetElement.replaceAll("\\s+","");
                            String to = transitionElements[2];
                            to = to.replaceAll("\\s+","");
                            Transition transition = new Transition(from, alphabetElement, to);
                            this.transitions.add(transition);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public List<String> getStates() {
        return states;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public String getInitialState() {
        return initialState;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public String getFilename() {
        return filename;
    }
}
