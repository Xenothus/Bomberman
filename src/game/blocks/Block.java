package game.blocks;

/**
 * Created by User on 04.04.2017.
 */
public abstract class Block
{
    protected byte species;
    protected boolean destroyable;
    protected boolean walkable;
    protected boolean player = false;
    protected boolean playerOnBomb = false;

    public boolean isDestroyable()
    {
        return destroyable;
    }

    public boolean isWalkable()
    {
        return walkable;
    }

    public boolean isPlayer()
    {
        return player;
    }

    public boolean isPlayerOnBomb()
    {
        return playerOnBomb;
    }

    public byte getSpecies()
    {
        return species;
    }

    public int getPlayerID()
    {
        return -1;
    }
}
