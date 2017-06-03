package server;

/**
 * Created by Oem on 2017-06-03.
 */
public class Config
{
    private Config(){}

    final static String DEFAULT_SERVER_IP = "127.0.0.1";
    final static int MAIN_PORT = 8888;
    final static int CLIENTS_MAX_NUM = 4;
    final static int INITIAL_UDP_PORT = 8000;
    final static int BUFFER_SIZE_UDP = 1024;
}
