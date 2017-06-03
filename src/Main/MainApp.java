package Main;

import Server.*;

public class MainApp {

    public static void main (String args[])
    {
        new Thread(new ServerMainThread()).start();
    }
}
