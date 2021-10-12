package hashTable;

import com.sun.jdi.Value;

import java.util.ArrayList;
import java.util.Collections;

public class HashTable {

    private int initialSize;
    private int size;
    private float loadFactor;
    ArrayList<HashNode<String,Integer>> table = new ArrayList<>();

    public HashTable()
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
        ArrayList<HashNode<String,Integer>> aux = new ArrayList<>();
        aux.addAll(table);
        table = new ArrayList<>();
        initialSize = initialSize*2;
        for(int i =0;i<initialSize;i++)
        {
            table.add(null);
        }
        for(HashNode<String,Integer> node : aux)
        {
            while(node!= null)
            {
                add(node.key);
                node = node.getNext();
            }
        }
    }

    public int add(String key)
    {
        //System.out.println("added");
        int position = hashFunction(key);
        HashNode<String,Integer> node = getItemOnPosition(position);
        HashNode<String,Integer> addNode = new HashNode<>(key,position);
        if(node == null)
        {
            table.set(position,addNode);
            size ++;
        }
        else
        {
            while (node.getNext()!= null)
            {
                node = node.getNext();
            }
            node.setNext(addNode);
            size++;
        }
        float load =(float)size/initialSize;
        if(load > loadFactor)
            reHash();
        return position;
    }



    public int getPosition(String key)
    {
        int position = hashFunction(key);
        HashNode<String, Integer> node = getItemOnPosition(position);
        if(node == null)
            position = add(key);
        else
        {
            while(node!=null)
            {
                if(node.key.equals(key))
                    return position;
                else
                    node = node.getNext();
            }
            position = add(key);
        }
        return position;
    }

    public HashNode<String,Integer> getItemOnPosition(int position)
    {
        return this.table.get(position);
    }

    private int hashFunction(String key)
    {
        int sum = key.chars().sum();
        //System.out.println(sum);
        return  (sum %initialSize);
    }
}
