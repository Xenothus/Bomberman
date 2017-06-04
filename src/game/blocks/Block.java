package game.blocks;

/**
 * Created by User on 04.04.2017.
 */
public abstract class Block
{
    protected byte species;
    protected boolean destroyable;
    protected boolean walkable;

    public boolean isDestroyable()
    {
        return destroyable;
    }

    public byte getSpecies()
    {
        return species;
    }

    public int getPlayerID()
    {
        return -1;
    }

    public boolean isWalkable()
    {
        return walkable;
    }
}
