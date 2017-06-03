package game.main;

import server.*;

public class MainApp {

    public static void main (String args[])
    {
        World.getInstance();    //first initialization of world
        new Thread(new ServerMainThread()).start();
    }
}
