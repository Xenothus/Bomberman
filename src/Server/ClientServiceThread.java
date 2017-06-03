package Server;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class ClientServiceThread implements Runnable {

    private final static String DEFAULT_SERVER_IP = "127.0.0.1";
    private final static int BUFFER_SIZE_UDP = 1024;

    private Socket socket;
    private int portUDP;

    public ClientServiceThread(Socket socket, int portUDP)
    {
        this.socket = socket;
        this.portUDP = portUDP;
    }

    @Override
    public void run()
    {
        connectWithClient();

        try (DatagramSocket socket = new DatagramSocket(portUDP))
        {
            InetAddress ip = InetAddress.getByName(DEFAULT_SERVER_IP);
            byte[] buffer = new byte[BUFFER_SIZE_UDP];
            while (true)
            {
                //Receiving order message from client
                DatagramPacket dp = new DatagramPacket(buffer, BUFFER_SIZE_UDP);
                socket.receive(dp);
                String receivedOrder = new String(dp.getData(), 0, dp.getLength());
                System.out.println(receivedOrder);

                //Sending OK response to client
                String message = "OK";
                DatagramPacket dps = new DatagramPacket(
                        message.getBytes(), message.length(), ip, portUDP);
                socket.send(dp);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void connectWithClient()
    {
        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
             DataOutputStream out = new DataOutputStream(
                     new BufferedOutputStream(socket.getOutputStream())))
        {
            System.out.println(in.readUTF());
            out.writeUTF(Integer.toString(portUDP));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
