package server;

import game.main.World;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Oem on 2017-06-03.
 */

import static server.Config.*;
import static game.main.Config.*;

public class ServerDataSenderThread implements Runnable {

    private InetAddress clientIP;
    private int portUDP;

    public ServerDataSenderThread(InetAddress ip, int portUDP)
    {
        clientIP = ip;
        this.portUDP = portUDP;
    }

    @Override
    public void run()
    {
        try (DatagramSocket socket = new DatagramSocket())
        {
            // TODO server has to send data to client, so you need to get client IP and put it here
            byte[] buffer = new byte[BUFFER_SIZE_UDP];

            while (true)
            {
                //Sending data to client
                byte[][] array2D = World.getInstance().getViewTable();
                int bufferIt = 0;
                for (int i = 0; i < COLS; i++)
                {
                    for (int k = 0; k < ROWS; k++)
                    {
                        buffer[bufferIt] = array2D[i][k];
                        bufferIt++;
                        //System.out.println(bufferIt);
                    }
                }

                DatagramPacket dps = new DatagramPacket(
                        buffer, BUFFER_SIZE_UDP, clientIP, portUDP);
                socket.send(dps);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
