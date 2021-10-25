package scanner;

import errors.LexicalError;
import hashTable.SymbolTable;
import pif.Pif;
import utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
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
        splitPattern = Pattern.compile("(?<=[\\s*[^a-zA-Z0-9]+\\s*])|(?=[\\s*[^a-zA-Z0-9]+\\s*])");

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
                String[] result = line.split(splitPattern.pattern());
                for(var token: result)
                {
                    if(token.matches("\\s*"))
                        continue;
                    var transformedToken = token.split(" ")[0];
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
                        int position = symbolTable.getPosition(token);
                        pif.addPif(token,position);
                        continue;
                    }
                    //string
                    matcher = Utils.stringConstant.matcher(token);
                    match = matcher.matches();
                    if(match)
                    {
                        int position = symbolTable.getPosition(token);
                        pif.addPif(token,position);
                        continue;
                    }
                    //identifier
                    matcher = Utils.identifier.matcher(token);
                    match = matcher.matches();
                    if(match)
                    {
                        int position = symbolTable.getPosition(token);
                        pif.addPif(token,position);
                        continue;
                    }

                    pif.writeToFile();
                    symbolTable.writeToFile();
                    reader.close();
                    throw new LexicalError("at line:" + lineCount + " token " + token + " could not be recognised");
                }
                //System.out.println(Arrays.toString(result));
            }
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
