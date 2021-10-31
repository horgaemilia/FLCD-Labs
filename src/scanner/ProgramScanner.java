package scanner;

import errors.LexicalError;
import hashTable.PositionTuple;
import hashTable.SymbolTable;
import pif.Pif;
import utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgramScanner {
    private ArrayList<String> acceptedTokens;
    private String tokenFile = "Tokens.in";
    private SymbolTable symbolTable;
    private Pif pif;
    private String programFile;
    private Pattern splitPattern;

    public ProgramScanner(String programFile)
    {
        this.programFile = programFile;
        this.tokenFile = "Tokens.in";
        this.symbolTable = new SymbolTable();
        this.pif = new Pif();
        this.acceptedTokens = new ArrayList<>();
        initializeTokens();
        splitPattern = Pattern.compile("(?<=[\\s*(\\\\*)|[^a-zA-Z0-9]+\\s*])|(?=[\\s*[^a-zA-Z0-9]+\\s*])");

    }

    private void initializeTokens()
    {
        try
        {
            File file = new File(tokenFile);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine())
            {
                String token = reader.nextLine();
                acceptedTokens.add(token);
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void analyzeLine(String line,int lineCount)
    {
        String[] splitBySpace = line.split("\\s+");
        Deque<String> unclassified = new LinkedList<>(Arrays.asList(splitBySpace));
        while (!unclassified.isEmpty())
        {
            String toAnalyze = unclassified.getFirst();
            unclassified.removeFirst();
            if(toAnalyze.isEmpty() || toAnalyze.isBlank())
                continue;
            if(acceptedTokens.contains(toAnalyze))
            {
                pif.addPif(toAnalyze);
                continue;
            }
            Matcher matcher = Utils.integerConstant.matcher(toAnalyze);
            boolean match = matcher.matches();
            if(match)
            {
                PositionTuple position = symbolTable.getPosition(toAnalyze);
                pif.addPif("constant",position);
                continue;
            }
            //string
            matcher = Utils.stringConstant.matcher(toAnalyze);
            match = matcher.matches();
            if(match)
            {
                PositionTuple position = symbolTable.getPosition(toAnalyze);
                pif.addPif("constant",position);
                continue;
            }
            //identifier
            matcher = Utils.identifier.matcher(toAnalyze);
            match = matcher.matches();
            if(match)
            {
                PositionTuple position = symbolTable.getPosition(toAnalyze);
                pif.addPif("identifier",position);
                continue;
            }

            boolean added = false;
            for (int index = 0; index < Utils.separators.size(); index++)
            {
                String separator = Utils.separators.get(index);
                if (toAnalyze.contains(separator))
                {
                    String separator_regex = Utils.separatorsRegex.get(index);
                    String splitPattern = "(?<=[" + separator_regex+"])" +"|(?=["+separator_regex+"])";
                    String[] splits = toAnalyze.split(splitPattern,-1);
                    for(int i =splits.length-1;i>=0;i--)
                    {
                        unclassified.addFirst(splits[i]);
                    }

                    added = true;
                    break;
                }
            }

            if (added)
                continue;
            throw new LexicalError("at line:" + lineCount + " token " + toAnalyze + " could not be recognised");
        }
    }

    public void scan() throws LexicalError
    {
        try
        {
            int lineCount = 0;
            File program = new File(programFile);
            Scanner reader = new Scanner(program);
            while (reader.hasNextLine())
            {
                lineCount++;
                String line = reader.nextLine();
               /* String[] result = line.split(splitPattern.pattern());
                for(var token: result)
                {
                    if(token.matches("\\s*"))
                        continue;
                   /* var transformedToken = token.split(" ")[0];
                    if(acceptedTokens.contains(token))
                    {
                        pif.addPif(token);
                        continue;
                    }
                    //now we check to see if it is constant
                    //int
                    Matcher matcher = Utils.integerConstant.matcher(token);
                    boolean match = matcher.matches();
                    if(match)
                    {
                        PositionTuple position = symbolTable.getPosition(token);
                        pif.addPif("constant",position);
                        continue;
                    }
                    //string
                    matcher = Utils.stringConstant.matcher(token);
                    match = matcher.matches();
                    if(match)
                    {
                        PositionTuple position = symbolTable.getPosition(token);
                        pif.addPif("constant",position);
                        continue;
                    }
                    //identifier
                    matcher = Utils.identifier.matcher(token);
                    match = matcher.matches();
                    if(match)
                    {
                        PositionTuple position = symbolTable.getPosition(token);
                        pif.addPif("identifier",position);
                        continue;
                    }
                    */
                    try
                    {
                        analyzeLine(line,lineCount);
                    }
                    catch (LexicalError err)
                    {
                        pif.writeToFile();
                        symbolTable.writeToFile();
                        reader.close();
                        throw new LexicalError(err.getMessage());
                    }
                    //throw new LexicalError("at line:" + lineCount + " token " + token + " could not be recognised");
                }
                //System.out.println(Arrays.toString(result));
            System.out.println("Lexically correct");
            reader.close();
            pif.writeToFile();
            symbolTable.writeToFile();
        }
     catch (FileNotFoundException e) {
        e.printStackTrace();
        }
    }
}
