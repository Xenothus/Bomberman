package server;

/**
 * Created by Oem on 2017-06-04.
 */
public class ClientsInfo
{
    private int clientsCount;

    private static class SingletonHelper {
        private static final ClientsInfo instance = new ClientsInfo();
    }

    public synchronized static ClientsInfo getInstance()
    {
        return SingletonHelper.instance;
    }

    private ClientsInfo()
    {
        clientsCount = 0;
    }

    public synchronized int getClientsCount()
    {
        return clientsCount;
    }

    public synchronized void decrementClientsCount()
    {
        clientsCount--;
    }

    public synchronized void incrementClientsCount()
    {
        clientsCount--;
    }
}
