package pif;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Pif {
    private ArrayList<PifEntrance> entrances = new ArrayList<>();
    private String fileName = "Pif.out";

    public void addPif(String token)
    {
        entrances.add(new PifEntrance(token,-1));
    }

    public void addPif(String token,int position)
    {
        entrances.add(new PifEntrance(token,position));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        entrances.forEach(s-> stringBuilder.append(s.toString()));
        return stringBuilder.toString();
    }

    public void writeToFile()
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            entrances.forEach(s-> {
                try {
                    writer.write(s.toString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
