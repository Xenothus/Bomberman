package game.main;

import game.auxiliary.Position;

public class Config
{
    private Config(){}

    // Max number of players
    public final static int MAX_PLAYERS_COUNT = 4;

    // World's dimensions
    public final static int COLS = 20;
    public final static int ROWS = 15;

    // Block types
    public final static int BLOCKS_TYPE_COUNT = 17;

    public final static byte CLEAR = 0;
    public final static byte BOMB = 1;
    public final static byte FLAME = 2;
    public final static byte WOOD = 3;
    public final static byte BRICK = 4;
    public final static byte EXTRA_BOMB = 5;
    public final static byte WOOD_WITH_EXTRA_BOMB = 6;
    public final static byte EXTRA_GUNPOWDER = 7;
    public final static byte WOOD_WITH_EXTRA_GUNPOWDER = 8;
    public final static byte[] BOMBERMEN = {9, 10, 11, 12};
    public final static byte[] BOMBERMEN_ON_BOMB = {13, 14, 15, 16};

    // Players' names
    public final static String[] PLAYER_NAMES = {
            "Kasia",
            "Lukasz",
            "Andrzej",
            "Patryk"
    };

    // Players initial positions
    public final static Position[] PLAYERS_INITIAL_POSITIONS = {
            new Position(1, 1),
            new Position(COLS - 2, 1),
            new Position(1, ROWS - 2),
            new Position(COLS - 2, ROWS - 2),
    };

    // Players actions
    public final static byte MOVE_UP = 0;
    public final static byte MOVE_DOWN = 1;
    public final static byte MOVE_LEFT = 2;
    public final static byte MOVE_RIGHT = 3;
    public final static byte PLANT_BOMB = 4;

    // Duration values
    public final static int BOMB_FIRING_DURATION = 2000;
    public final static int BOMB_BLAST_DURATION = 1000;

    // Position counting helper
    public final static int[][] POSITIONS = {
            {0, -1},
            {0, 1},
            {-1, 0},
            {1, 0}
    };

    // X & Y index values
    public final static byte X = 0;
    public final static byte Y = 1;




}
