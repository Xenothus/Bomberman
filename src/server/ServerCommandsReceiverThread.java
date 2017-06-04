package server;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import game.main.World;

import static server.Config.*;

public class ServerCommandsReceiverThread implements Runnable {

    private Socket socket;
    private InetAddress clientIP;
    private int portUDP;
    private int clientID;

    public ServerCommandsReceiverThread(Socket socket, int portUDP, int clientID)
    {
        this.socket = socket;
        this.portUDP = portUDP;
        this.clientID = clientID;
    }

    @Override
    public void run()
    {
        connectWithClient();
        new Thread(new ServerDataSenderThread(clientIP, portUDP + 1)).start();

        try (DatagramSocket socket = new DatagramSocket(portUDP))
        {
            InetAddress ip = InetAddress.getByName(DEFAULT_SERVER_IP);
            byte[] buffer = new byte[BUFFER_SIZE_UDP];

            while (true)
            {
                //Receiving order message from client
                DatagramPacket dp = new DatagramPacket(buffer, BUFFER_SIZE_UDP);
                socket.receive(dp);
                byte command = buffer[0];

                World.getInstance().executeCommand(clientID, command);
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
            String received = in.readUTF();
            clientIP = InetAddress.getByName(received);
            out.writeUTF(Integer.toString(portUDP));
            System.out.println("Client connected: " + received);

            World.getInstance().addNewPlayer(clientID);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
