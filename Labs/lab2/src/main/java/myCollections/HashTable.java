package myCollections;

import java.util.ArrayList;

class Node <T1, T2>
{
    T1 _data;
    T2 _key;
    public Node(T1 data, T2 key)
    {
        _data = data;
        _key = key;
    }
    public void setData(T1 data) {
        this._data = data;
    }

    public void setKey(T2 key) {
        this._key = key;
    }

    public T1 getData() {
        return _data;
    }

    public T2 getKey() {
        return _key;
    }
}
class HashTable <T1, T2> {

    private ArrayList<Node<T1, T2>> []hashTable;
    private int _capacity = 4;
    private int _size;
    private final double loadCoef = 0.75;

    public HashTable()
    {
        hashTable = new ArrayList[4];
        _size = 0;
        for(int i = 0; i < _capacity; i++)
        {
            hashTable[i] = new ArrayList<Node<T1, T2>>();
        }
    }


    private int hashFunc(T2 key)
    {
        return Math.abs(key.hashCode() % _capacity);
    }

    private void rehashing()
    {
        _capacity *= 2;
        int hash = 0;
        ArrayList<Node<T1, T2>> []newHashTable = new ArrayList[_capacity];;

        for(int i = 0; i < _capacity; i++)
        {
            newHashTable[i] = new ArrayList<Node<T1, T2>>();
        }


        for(ArrayList<Node<T1, T2>> lst: hashTable)
        {
            for(Node<T1, T2> nd: lst)
            {
                hash = hashFunc(nd._key);
                newHashTable[hash].add(nd);
            }
        }
        hashTable = newHashTable;
    }
    public void add(T1 data, T2 key)
    {
        int hash = 0;
        if(_capacity * loadCoef < _size + 1)
            rehashing();

        hash = hashFunc(key);
        hashTable[hash].add(new Node(data, key));
        _size++;
    }

    public void remove(T2 key)
    {
        int hash = hashFunc(key);

        for(int i = 0; i < hashTable[hash].size(); i++)
        {
            if(hashTable[hash].get(i)._key.equals(key))
            {
                hashTable[hash].remove(i);
                _size--;
                return;
            }

        }
    }

    public boolean contains(T2 key)
    {
        int hash = hashFunc(key);

        for(Node el: hashTable[hash])
        {
            if(el.getKey().equals(key))
                return true;
        }

        return false;
    }

    public int size()
    {
        return _size;
    }

}
