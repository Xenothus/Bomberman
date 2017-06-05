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

    public ServerCommandsReceiverThread(Socket socket, int portUDP)
    {
        this.socket = socket;
        this.portUDP = portUDP;
    }

    @Override
    public void run()
    {
        if (!connectWithClient())
            return;

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

    private boolean connectWithClient()
    {
        boolean result = true;
        String toSendMsg;

        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
             DataOutputStream out = new DataOutputStream(
                     new BufferedOutputStream(socket.getOutputStream())))
        {
            //Receiving client IP address and player character selection
            String received = in.readUTF();
            String clientIPString = received.split("-")[0];
            String selectedPlayerString = received.split("-")[1];
            clientIP = InetAddress.getByName(clientIPString);

            //Check if player character is available, if yes send UDP port, else denial msg
            int selectedPlayer = Integer.parseInt(selectedPlayerString);
            if (!World.getInstance().takePlayerCharacter(selectedPlayer))
            {
                toSendMsg = PLAYER_TAKEN_MSG;
                result = false;
            }
            else
                toSendMsg = Integer.toString(portUDP);

            //Sending answer message
            out.writeUTF(toSendMsg);

            if (!result)
                throw new Exception();

            //If success add player to world
            System.out.println("Client connected: " + clientIPString);
            clientID = selectedPlayer;
            World.getInstance().addNewPlayer(clientID);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            ClientsInfo.getInstance().decrementClientsCount();
        }

        return result;
    }
}
