package game.blocks;

import static game.main.Config.BOMBERMEN;

/**
 * Created by Oem on 2017-06-04.
 */
public class Bomberman extends Block
{
    private int playerID;

    public Bomberman(int playerID)
    {
        this.playerID = playerID;

        walkable = false;
        player = true;
        species = BOMBERMEN[playerID];
        destroyable = true;
    }

    public int getPlayerID()
    {
        return playerID;
    }
}
