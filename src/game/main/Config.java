package game.main;

import game.auxiliary.Position;

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
    public final static byte BOMBERMAN_ON_BOMB = 6;

    public final static Position[] PLAYERS_INITIAL_POSITIONS = {
            new Position(1, 1),
            new Position(COLS - 2, 1),
            new Position(1, ROWS - 2),
            new Position(COLS - 2, ROWS - 2),
    };

    public final static int BOMB_FIRING_DURATION = 2000;

    public final static byte MOVE_UP = 1;
    public final static byte MOVE_DOWN = 2;
    public final static byte MOVE_LEFT = 3;
    public final static byte MOVE_RIGHT = 4;
    public final static byte PLANT_BOMB = 5;

    public final static byte UP = 0;
    public final static byte DOWN = 1;
    public final static byte LEFT = 2;
    public final static byte RIGHT = 3;
}
