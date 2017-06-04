package game.blocks;

import static game.main.Config.BOMBERMAN;

/**
 * Created by Oem on 2017-06-04.
 */
public class Bomberman extends Block
{
    private int playerID;

    public Bomberman(int playerID)
    {
        this.playerID = playerID;
        player = true;

        walkable = false;
        species = BOMBERMAN;
        destroyable = true;
    }

    public int getPlayerID()
    {
        return playerID;
    }
}
