package first;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Grammar {
    private List<String> terminalSymbols = new ArrayList<>();
    private List<String> nonterminalSymbols = new ArrayList<>();
    private String startingSymbol;
    private List<Production> productions = new ArrayList<>();
    private String filename;

    public Grammar(String filename)
    {
        this.filename = filename;
        readGrammar();
    }

    private void readGrammar()
    {
        String mode = "";
        File program = new File(filename);
        try {
            Scanner reader = new Scanner(program);
            while (reader.hasNextLine())
            {
                String line = reader.nextLine();
                if(line.contains("Terminals"))
                {
                    mode = "Terminals";
                    continue;
                }
                if(line.contains("Nonterminals"))
                {
                    mode = "Nonterminals";
                    continue;
                }
                if(line.contains("Start"))
                {
                    mode = "Start";
                    continue;
                }
                if(line.contains("Productions"))
                {
                    mode = "Productions";
                    continue;
                }
                switch (mode) {
                    case "Terminals" -> {
                        if(line.equals("\"\""))
                            this.terminalSymbols.add(" ");
                        else {
                            this.terminalSymbols.add(line);
                        }
                    }
                    case "Nonterminals" -> {
                        this.nonterminalSymbols.add(line);
                    }
                    case "Start" -> {
                        this.startingSymbol = line;
                    }
                    case "Productions" -> {
                        String[] rule = line.split("->");
                        if(rule.length == 2)
                        {
                            String nonTerminal = rule[0];
                            List<String> right = splitRuleLine(nonTerminal);
                            String symbols = rule[1];
                            //now we need to check if we have more rules
                            //ex: S->0A|1S we need to split in S->0A and S->1S
                            String[] rules = rule[1].split("\\|");
                            for(int i=0;i<rules.length;i++)
                            {
                                List<String> ruleElements = splitRuleLine(rules[i]);
                                Production p = new Production();
                                p.setLeft(right);
                                p.setOrderedSymbols(ruleElements);
                                this.productions.add(p);
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<String> splitRuleLine(String line)
    {
        String[] generatedElements = line.split(" ");
        List<String> elements = new ArrayList<>();
        for(int i=0;i<generatedElements.length;i++)
        {
            String element = generatedElements[i];
            element = element.replace(" ","");
            if(element.equals("\"\""))
                elements.add(" ");
            else {
                elements.add(element);
            }
        }
        return elements;
    }

    public List<Production> getProductionsForNonTerminal(String nonTerminal)
    {
        return this.productions.stream().filter(prod -> prod.getLeft().size()==1 && prod.getLeft().get(0).equals(nonTerminal)).collect(Collectors.toList());
    }

    public List<String> getTerminalSymbols() {
        return terminalSymbols;
    }

    public List<String> getNonterminalSymbols() {
        return nonterminalSymbols;
    }

    public List<String> getFirstProduction(String nonTerminal)
    {
        return this.productions.stream().filter(production -> production.getLeft().size()==1 && production.getLeft().get(0).equals(nonTerminal)).findFirst().get().getOrderedSymbols();
    }

    public String getFirstProductionAsString(String nonTerminal)
    {
        return this.productions.stream().filter(production -> production.getLeft().size()==1 && production.getLeft().get(0).equals(nonTerminal)).findFirst().get().getOrderedSymbolsAsAString();
    }
    public String getStartingSymbol() {
        return startingSymbol;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public boolean isContextFree() {
        return productions.stream().allMatch(p ->
            p.getLeft().size() == 1 && nonterminalSymbols.contains(p.getLeft().get(0))
        );
    }
}
