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

    private final ClientsInfo clientsInfo = ClientsInfo.getInstance();

    public ServerMainThread()
    {
        nextPortUDP = INITIAL_UDP_PORT;
    }

    @Override
    public void run()
    {
        try (ServerSocket serverSocket = new ServerSocket(MAIN_PORT))
        {
            System.out.println("Server is running \nIP: " + DEFAULT_SERVER_IP + " Port: " + MAIN_PORT);
            System.out.println("////////////////////////////////////////");

            while (true)
            {
                if (clientsInfo.getClientsCount() == CLIENTS_MAX_NUM)
                    continue;

                final Socket socket = serverSocket.accept();
                if (socket != null)
                {
                    executor.submit(new ServerCommandsReceiverThread(socket, getNextPortUDP()));
                    clientsInfo.incrementClientsCount();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private int getNextPortUDP()
    {
        nextPortUDP += 2;
        return nextPortUDP;
    }
}
