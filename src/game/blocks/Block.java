package game.blocks;

import game.auxiliary.Position;

public abstract class Block
{
    protected Position position;
    protected byte species;

    protected boolean destroyable;
    protected boolean walkable;

    protected boolean player = false;
    protected boolean playerOnBomb = false;

    public Position getPosition()
    {
        return position;
    }

    public byte getSpecies()
    {
        return species;
    }

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
}
