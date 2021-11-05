package hashTable;

import com.sun.jdi.Value;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SymbolTable {

    private int initialSize;
    private int size;
    private float loadFactor;
    ArrayList<HashNode<String,PositionTuple>> table = new ArrayList<>();
    private String fileName = "ST.out";

    public SymbolTable()
    {
        initialSize = 10;
        size = 0;
        loadFactor = 0.75f;
        for(int i =0;i<initialSize;i++)
        {
            table.add(null);
        }
    }

    private void reHash()
    {
        ArrayList<HashNode<String,PositionTuple>> aux = new ArrayList<>();
        aux.addAll(table);
        table = new ArrayList<>();
        initialSize = initialSize*2;
        for(int i =0;i<initialSize;i++)
        {
            table.add(null);
        }
        for(HashNode<String,PositionTuple> node : aux)
        {
            while(node!= null)
            {
                add(node.key);
                node = node.getNext();
            }
        }
    }

    public PositionTuple add(String key)
    {
        //System.out.println("added");
        PositionTuple position = new PositionTuple(hashFunction(key),0);
        HashNode<String,PositionTuple> node = getBucket(position.getBucketPosition());
        HashNode<String,PositionTuple> addNode = new HashNode<>(key,position);
        if(node == null)
        {
            table.set(position.getBucketPosition(),addNode);
            size ++;
        }
        else
        {
            int inBucketPosition = 1;
            while (node.getNext()!= null)
            {
                node = node.getNext();
                inBucketPosition++;
            }
            node.setNext(addNode);
            addNode.value.setInBucketPosition(inBucketPosition);
            position.setInBucketPosition(inBucketPosition);
            size++;
        }
        /*float load =(float)size/initialSize;
        if(load > loadFactor)
        {
            reHash();
        }
        */
        return position;
    }



    public PositionTuple getPosition(String key)
    {
        PositionTuple position = new PositionTuple(hashFunction(key),0);
        HashNode<String, PositionTuple> node = getBucket(position.getBucketPosition());
        if(node == null)
            position = add(key);
        else
        {
            while(node!=null)
            {
                if(node.key.equals(key))
                {
                    return node.value;
                }
                else
                {
                    node = node.getNext();
                }
            }
            position = add(key);
        }
        return position;
    }

    public HashNode<String,PositionTuple> getBucket (int position)
    {
        return this.table.get(position);
    }

    private int hashFunction(String key)
    {
        int sum = key.chars().sum();
        //System.out.println(sum);
        return  (sum %initialSize);
    }

    public void writeToFile()
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for(var node : table)
            {
                if(node == null)
                {
                    writer.write("null \n");
                }
                else
                {
                    if(node.getNext() == null)
                        writer.write(node.key + ": " + node.value.toString() + "\n");
                    else {
                        writer.write(node.key + ": " + node.value.toString() + " -> " );
                        node = node.getNext();
                        while (node != null) {
                            writer.write(node.key + ": " + node.value.toString());
                            if(node.getNext() != null)
                                writer.write("->");
                            node = node.getNext();
                        }
                        writer.write("\n");
                    }
                }
            }
            writer.close();
        }
        catch (IOException ignored)
        {
            System.out.println(ignored);
        }
    }
}
