package myCollections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Random;

public class LinkedHashSetTest {

    private LinkedHashSet<String> actual;
    private myCollections.LinkedHashSet<String> col;
    private LinkedList<String> lst;
    private LinkedList<String> otherLst;
    String smthString;
    @Before
    public void setUp()
    {
        actual = new LinkedHashSet<>();
        col = new myCollections.LinkedHashSet<>();
        lst = new LinkedList<>();
        otherLst = new LinkedList<>();

        final int SIZE = 10;
        byte[] array = new byte[7];


        for(int i = 0; i < SIZE; i++)
        {
            //Генерация случайных строк
            new Random().nextBytes(array);
            String data = new String(array, StandardCharsets.UTF_8);

            actual.add(data);
            col.add(data);

            if((i % 4) < 2)
            {
                actual.remove(data);
                col.remove(data);

                //Строки, которых нет в коллекциях (для проверки addAll)
                otherLst.add(data);
            }
            else
            // Строка, которая точно содержится в обеих коллекциях (для проверки contains)
            {
                smthString = data;
                //Строки, которые точно содержатся в обеих коллекциях (для проверки containsAll)
                if(i % 2 != 0)
                    lst.add(data);
            }
        }
    }
    @Test
    public void size() {

        Assert.assertEquals(actual.size(), col.size());
    }

    @Test
    public void foreachTest(){
        Iterator actItr = actual.iterator();
        Iterator colItr = col.iterator();
        while(actItr.hasNext() || colItr.hasNext())
        {
            Assert.assertEquals(actItr.next(), colItr.next());
        }
    }
    @Test
    public void isEmpty() {
        //Проверка, когда коллекции не пустые
        Assert.assertEquals(actual.isEmpty(), col.isEmpty());
        Iterator itr = actual.iterator();

        while(itr.hasNext())
        {
            String str = (String)itr.next();
            col.remove(str);
            itr.remove();
        }
        //Проверка, когда коллекции пустые
        Assert.assertEquals(col.isEmpty(), actual.isEmpty());
    }

    @Test
    public void contains() {
        Assert.assertEquals(actual.contains(smthString), col.contains(smthString));
        actual.remove(smthString);
        col.remove(smthString);
        Assert.assertEquals(actual.contains(smthString), col.contains(smthString));

    }

    @Test
    public void add() {
        smthString = "FSDFSDFASDFASF";
        int size = actual.size();
        col.add(smthString);
        actual.add(smthString);

        Assert.assertEquals(col.size(), size + 1);
        Assert.assertEquals(actual.size(), size + 1);
        Assert.assertEquals(actual.contains(smthString),col.contains(smthString));

    }

    @Test
    public void remove() {
        int size = actual.size();

        Assert.assertEquals(actual.contains(smthString),col.contains(smthString));
        col.remove(smthString);
        actual.remove(smthString);
        Assert.assertEquals(col.size(), size - 1);
        Assert.assertEquals(actual.size(), size - 1);
        Assert.assertEquals(actual.contains(smthString),col.contains(smthString));

    }

    @Test
    public void containsAll() {
        Assert.assertEquals(actual.containsAll(lst), col.containsAll(lst));
        lst.removeFirst();
        Assert.assertEquals(actual.containsAll(lst), col.containsAll(lst));
    }

    @Test
    public void addAll() {
        Assert.assertEquals(actual.containsAll(otherLst), col.containsAll(otherLst));
        col.addAll(otherLst);
        actual.addAll(otherLst);
        Assert.assertEquals(actual.containsAll(otherLst), col.containsAll(otherLst));
    }

    @Test
    public void retainAll() {
        actual.retainAll(lst);
        col.retainAll(lst);
        Iterator itr = col.iterator();

        Assert.assertEquals(col.size(), actual.size());
        //Тут заодно проверяется порядок обхода элементов в коллекциях (он должен быть одинаковый)
        for(String str: actual)
        {
            Assert.assertEquals(str, itr.next());
        }
    }

    @Test
    public void removeAll() {

        Assert.assertEquals(actual.containsAll(lst), col.containsAll(lst));
        actual.removeAll(lst);
        col.removeAll(lst);
        Iterator itr = actual.iterator();
        for(String str: col)
        {
            Assert.assertEquals(str, (String)itr.next());
        }
    }

    @Test
    public void toArray() {
        Object []arr1 = actual.toArray();
        Object []arr2 = actual.toArray();

        for(int i = 0; i < arr1.length; i++)
        {
            Assert.assertEquals(arr1[i], arr2[i]);
        }

    }
    @Test

    public void clear() {

        col.clear();
        actual.clear();
        Assert.assertEquals(actual.isEmpty(), col.isEmpty());
    }

}