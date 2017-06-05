package server;

import game.main.World;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import static server.Config.*;
import static game.main.Config.*;

public class ServerDataSenderThread implements Runnable {

    private final ClientsInfo clientsInfo = ClientsInfo.getInstance();
    private int clientID;

    private InetAddress clientIPAddress;
    private int portUDP;

    ServerDataSenderThread(int clientID, InetAddress clientIPAddress, int portUDP)
    {
        this.clientID = clientID;
        this.clientIPAddress = clientIPAddress;
        this.portUDP = portUDP;
    }

    @Override
    public void run()
    {
        try (DatagramSocket socket = new DatagramSocket())
        {
            byte[] buffer = new byte[BUFFER_SIZE_UDP];

            while (clientsInfo.isConnected(clientID))
            {
                //Sending data to client
                byte[][] array2D = World.getInstance().getViewModel();
                int bufferIt = 0;
                for (int i = 0; i < COLS; i++)
                {
                    for (int k = 0; k < ROWS; k++)
                    {
                        buffer[bufferIt] = array2D[i][k];
                        bufferIt++;
                    }
                }

                DatagramPacket dps = new DatagramPacket(
                        buffer, BUFFER_SIZE_UDP, clientIPAddress, portUDP);
                socket.send(dps);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
