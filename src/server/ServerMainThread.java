package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static server.Config.*;

public class ServerMainThread implements Runnable
{
    private final ExecutorService executor = Executors.newFixedThreadPool(CLIENTS_MAX_NUM);
    private int nextPortUDP;
    private int nextClientID;
    private int clientsCount;

    public ServerMainThread()
    {
        nextPortUDP = INITIAL_UDP_PORT;
        nextClientID = 0;
        clientsCount = 0;
    }

    @Override
    public void run()
    {
        try (ServerSocket serverSocket = new ServerSocket(MAIN_PORT))
        {
            while (true)
            {
                if (clientsCount > 4)
                    continue;

                final Socket socket = serverSocket.accept();
                if (socket != null)
                {
                    executor.submit(new ServerCommandsReceiverThread(socket, getNextPortUDP(), getNextClientID()));
                    clientsCount++;
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
        nextPortUDP += 2;
        return nextPortUDP;
    }

    private int getNextClientID()
    {
        return nextClientID++;
    }
}
