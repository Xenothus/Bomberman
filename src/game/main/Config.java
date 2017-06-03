package game.main;

/**
 * Created by Oem on 2017-06-03.
 */
public class Config
{
    private Config(){}

    public final static int COLS = 20;
    public final static int ROWS = 15;

    public final static byte CLEAR = 0;
    public final static byte BOMB = 1;
    public final static byte FLAME = 2;
    public final static byte WOOD = 3;
    public final static byte BRICK = 4;
    public final static byte BOMBERMAN = 5;

    public final static byte MOVE_UP = 1;
    public final static byte MOVE_DOWN = 2;
    public final static byte MOVE_LEFT = 3;
    public final static byte MOVE_RIGHT = 4;
    public final static byte PLANT_BOMB = 5;
}
