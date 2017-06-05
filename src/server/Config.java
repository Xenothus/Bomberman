package server;

public class Config
{
    private Config(){}

    // Server IP Address (own address)
    final static String DEFAULT_SERVER_IP = "127.0.0.1";

    // Server port
    final static int MAIN_PORT = 8888;

    // Max number of clients
    public final static int CLIENTS_MAX_COUNT = 4;

    // First UPD port assigned by server for UDP connection with client
    final static int INITIAL_UDP_PORT = 8000;

    // Buffer size
    final static int BUFFER_SIZE_UDP = 1024;

    // Default disconnect command byte value
    final static byte DISCONNECT_COMMAND = (byte) 255;

    // Default server message for player taken refusal
    final static String PLAYER_TAKEN_MSG = "PN";
}
