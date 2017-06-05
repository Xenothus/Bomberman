package game.blocks;

import static game.main.Config.BOMBERMEN;

public class Bomberman extends Block
{
    private int playerID;

    public Bomberman(int playerID)
    {
        this.playerID = playerID;

        species = BOMBERMEN[playerID];

        walkable = false;
        destroyable = true;

        player = true;
    }

    public int getPlayerID()
    {
        return playerID;
    }
}
