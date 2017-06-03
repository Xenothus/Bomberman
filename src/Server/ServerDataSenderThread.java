package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Objects;

/**
 * Created by Oem on 2017-06-03.
 */
public class ServerDataSenderThread implements Runnable {

    private final static String DEFAULT_SERVER_IP = "127.0.0.1";

    private final static int BUFFER_SIZE_UDP = 1024;

    private int portUDP;

    public ServerDataSenderThread(int portUDP)
    {
        this.portUDP = portUDP;
    }

    @Override
    public void run()
    {
        try (DatagramSocket socket = new DatagramSocket())
        {
            // TODO Server has to send data to client, so you need to get client IP and put it here
            InetAddress ip = InetAddress.getByName(DEFAULT_SERVER_IP);
            byte[] buffer = new byte[BUFFER_SIZE_UDP];
            while (true)
            {
                //Sending data to client
                String message = "Some very important data";
                DatagramPacket dps = new DatagramPacket(
                        message.getBytes(), message.length(), ip, portUDP);
                socket.send(dps);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
