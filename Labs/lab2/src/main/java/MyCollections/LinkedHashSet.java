package MyCollections;

import java.util.*;


public class LinkedHashSet<T> implements Set<T>, Iterable<T> {
   final private int PRESENT = 1;
   HashTable<Integer, T> hashTable;
   LinkedList<T> lst;

   public LinkedHashSet()
   {
       hashTable = new HashTable<Integer, T>();
       lst = new LinkedList<T>();
   }

    public int size() {
        return hashTable.size();
    }

    public boolean isEmpty() {
        return lst.isEmpty();
    }

    public boolean contains(Object o) {
        return hashTable.contains((T)o);
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int currentInd = -1;
            private boolean nextWasUsed = false;
            public boolean hasNext()
            {
                return (lst.size() > currentInd + 1);
            }

            public T next() {
                if(currentInd + 1 < lst.size())
                {
                    nextWasUsed = true;
                    return lst.get(++currentInd);
                }
                else
                    throw new NoSuchElementException();
            }

            public void remove() {
                if(nextWasUsed)
                {
                    lst.remove(currentInd);
                    hashTable.remove(PRESENT, lst.get(currentInd));
                    nextWasUsed = false;
                }
                else
                    throw new IllegalStateException();

            }
        };
    }

    public Object[] toArray() {
        return lst.toArray();
    }

    public <T1> T1[] toArray(T1[] t1s) {
        if(t1s == null || t1s.length < lst.size())
        {
            t1s = (T1[]) new Object[lst.size()];
        }
        int i = 0;
        for(T item: lst)
        {
            t1s[i] = (T1)item;
        }
        return t1s;
    }

    public boolean add(T el) {
        if(hashTable.contains(el))
            return false;
        else
        {
            hashTable.add(PRESENT, el);
            lst.add(el);
            return true;
        }
    }

    public boolean remove(Object o) {
       if(hashTable.contains((T)o))
       {
           lst.remove(o);
           hashTable.remove(PRESENT, (T)o);
           return true;
       }
       else
           return false;

    }

    public boolean containsAll(Collection<?> collection) {
       if(collection == null)
           return false;
        for(Object item: collection)
        {
            if(!hashTable.contains((T)item))
                return false;
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> collection) {
       if(collection == null)
           return false;
        for(Object item: collection)
        {
            if(!hashTable.contains((T)item))
            {
                hashTable.add(PRESENT, (T)item);
            }
        }
        return true;

    }

    public boolean retainAll(Collection<?> collection) {
       if(collection == null)
        return false;
       lst.retainAll(collection);
       hashTable = new HashTable<Integer, T>();
       for(Object item: lst)
           hashTable.add(PRESENT, (T)item);
       return true;

    }

    public boolean removeAll(Collection<?> collection) {
        if(collection == null)
            return false;

        for(Object item: collection)
        {
            hashTable.remove(PRESENT, (T)item);
            lst.remove(item);
        }
        return true;
    }

    public void clear()
    {
       lst.clear();
       hashTable = new HashTable<Integer, T>();
    }

}
