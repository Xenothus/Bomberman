package game.blocks;

import game.auxiliary.Position;

import static game.main.Config.EXTRA_BOMB;

public class ExtraBomb extends Block
{
    public ExtraBomb(Position position)
    {
        this.position = position;

        species = EXTRA_BOMB;

        walkable = true;
        destroyable = true;
    }
}
