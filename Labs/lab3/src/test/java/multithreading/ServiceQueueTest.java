package multithreading;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.*;

public class ServiceQueueTest {
    private ServiceQueue sq;
    private LinkedList<String> clients;
    private byte[] array;
    private int maxIdClient = 10;

    @Before
    public void setUp()
    {
        sq = new ServiceQueue(maxIdClient);
        clients = new LinkedList<String>();
        array = new byte[7];

    }
    @Test
    public void raceTest1() throws InterruptedException {
        for(int i = 0; i < maxIdClient / 2; i++)
        {
            new Random().nextBytes(array);
            clients.addLast(new String(array, StandardCharsets.UTF_8));
            sq.addClient(clients.getLast());
        }
        sq.waitAll();
        Assert.assertEquals(sq.getServedClientsNumber(), maxIdClient / 2);
    }

    @Test
    public void raceTest2() throws InterruptedException {
        for(int i = 0; i < 2 * maxIdClient + maxIdClient / 2; i++)
        {
            new Random().nextBytes(array);
            clients.addLast(new String(array, StandardCharsets.UTF_8));
            sq.addClient(clients.getLast());
        }

        sq.waitAll();
        Assert.assertEquals(sq.getServedClientsNumber(), (2 * maxIdClient + maxIdClient / 2));

    }

}