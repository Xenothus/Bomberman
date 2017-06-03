package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMainThread implements Runnable {

    private final static int MAIN_PORT = 8888;

    private final static int CLIENTS_MAX_NUM = 4;

    private final ExecutorService executor = Executors.newFixedThreadPool(CLIENTS_MAX_NUM);
    private int nextPortUDP;

    public ServerMainThread()
    {
        nextPortUDP = 8000;
    }

    @Override
    public void run()
    {
        try (ServerSocket serverSocket = new ServerSocket(MAIN_PORT))
        {
            while (true)
            {
                final Socket socket = serverSocket.accept();
                if (socket != null)
                {
                    executor.submit(new ServerOrdersReceiverThread(socket, getNextPortUDP()));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //For future
    private int getNextPortUDP()
    {
        return nextPortUDP++;
    }
}
