package hashTable;

public class HashNode <K,V>
{
    K key;
    V value;
    private HashNode<K,V> next;

    public HashNode(K key,V value)
    {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    public HashNode<K,V> getNext()
    {
        return next;
    }

    public void setNext(HashNode<K,V> node)
    {
        this.next = node;
    }
}
