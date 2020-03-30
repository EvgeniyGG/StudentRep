package multithreading;

public class Program {
    public static void main(String[] args)
    {
        ServiceQueue queue = new ServiceQueue(4);
        queue.addClient("1");
        queue.addClient("2");
        queue.addClient("3");
        queue.addClient("4");
        queue.addClient("5");
        queue.addClient("6");
        queue.addClient("7");
        queue.addClient("8");
        queue.addClient("9");
        queue.addClient("10");
    }


}
