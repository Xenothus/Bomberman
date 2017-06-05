package server;

import game.main.World;
import static server.Config.*;

class ClientsInfo
{
    private static class SingletonHelper {
        private static final ClientsInfo instance = new ClientsInfo();
    }

    private int clientsCount;
    private boolean[] isConnected = new boolean[CLIENTS_MAX_COUNT];
    private Object[] isConnectedLocks = new Object[CLIENTS_MAX_COUNT];

    static ClientsInfo getInstance()
    {
        return SingletonHelper.instance;
    }

    private ClientsInfo()
    {
        clientsCount = 0;
        for (int i = 0; i < CLIENTS_MAX_COUNT; i++)
        {
            isConnected[i] = false;
            isConnectedLocks[i] = new Object();
        }
    }

    boolean takePlayerSlot(int ID)
    {
        synchronized (isConnectedLocks[ID])
        {
            if (isConnected[ID])
                return false;

            isConnected[ID] = true;
            World.getInstance().joinPlayerWithID(ID);
            return true;
        }
    }

    boolean releasePlayerSlot(int ID)
    {
        synchronized (isConnectedLocks[ID])
        {
            if (!isConnected[ID])
                return false;

            isConnected[ID] = false;
            World.getInstance().quitPlayerWithID(ID);
            return true;
        }
    }

    boolean isConnected(int ID)
    {
        synchronized (isConnectedLocks[ID])
        {
            return isConnected[ID];
        }
    }

    synchronized int getClientsCount()
    {
        return clientsCount;
    }

    synchronized void decrementClientsCount()
    {
        clientsCount--;
    }

    synchronized void incrementClientsCount()
    {
        clientsCount--;
    }
}
