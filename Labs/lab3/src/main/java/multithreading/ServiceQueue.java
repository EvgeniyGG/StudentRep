package multithreading;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class ServiceQueue {
    private Semaphore cashBox;
    private LinkedList<Client> threadBuff, queue;
    private int _maxClientId;
    private volatile int currentClientId = 1;
    private volatile int numberOfClients = 0;
    private volatile int numberOfServedClients = 0;
    private LinkedList<Thread> threads;
    private static volatile boolean  isRunning = true;

    public ServiceQueue(int maxClientId)
    {
        cashBox = new Semaphore(1, false);
        threadBuff = new LinkedList<Client>();
        queue = new LinkedList<Client>();
        _maxClientId = maxClientId;
        threads = new LinkedList<Thread>();
    }

    public void addClient(String name)
    {
        Thread thread;
        if(numberOfClients < _maxClientId && threadBuff.isEmpty())
        {
            queue.add(new Client(name, cashBox, numberOfClients++ % _maxClientId + 1));
            thread = new Thread(queue.getLast());
            threads.addLast(thread);
            thread.start();
        }
        else
            threadBuff.addLast(new Client(name, cashBox, numberOfClients++ % _maxClientId + 1));
    }

    private void fromBuffToQueue()
    {
        Thread thread;
        if(threadBuff.isEmpty())
            return;
        else
        {
            queue.addLast(threadBuff.getFirst());
            threadBuff.removeFirst();
            thread = new Thread(queue.getLast());
            threads.addLast(thread);
            thread.start();
        }
    }

    private void info(Client client, int time)
    {
        System.out.println("Total clients in the queue: " + (int)(numberOfClients - getServedClientsNumber()));
        System.out.println("Length of receipt tape: " + _maxClientId);
        System.out.println("Number on the table:" + currentClientId);
        System.out.println("Client ticket number: " + client.getClientId() + " name: " + client.getName() + "...");
        System.out.println("Service duration: " + time);
        System.out.println("Served " + getServedClientsNumber() + "\n");

    }
    private void serveClient(Client client)
    {
        try {
            int time = 1000 + new Random().nextInt(1000);
            Thread.sleep(time);
            info(client, time);
            currentClientId = currentClientId++ % _maxClientId + 1; ;
            numberOfServedClients++;
            client.isServed = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public int getServedClientsNumber()
    {
        return numberOfServedClients;
    }

    int getCurrentClientId()
    {
        return currentClientId;
    }

    public void waitAll()
    {
        while((numberOfClients - getServedClientsNumber() )!= 0);
    }

    private class Client implements Runnable{
        private Semaphore _cashBox;
        String _name;
        boolean isServed = false;
        private int _clientId;

        public int getClientId() {
            return _clientId;
        }

        public String getName() {
            return _name;
        }

        public Client(String name, Semaphore cashBox, int clientId)
        {
            _name = name;
            _cashBox = cashBox;
            _clientId = clientId;
        }

        public void run() {
            while(!isServed)
            {
                try {
                    _cashBox.acquire();
                    if(_clientId == currentClientId)
                    {
                        serveClient(this);
                        fromBuffToQueue();
                        queue.remove(this);
                        this.isServed = true;
                    }
                    _cashBox.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}


