package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Oem on 2017-06-03.
 */

import static server.Config.*;

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
                String message = "Some very important data";
                DatagramPacket dps = new DatagramPacket(
                        message.getBytes(), message.length(), clientIP, portUDP);
                socket.send(dps);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
