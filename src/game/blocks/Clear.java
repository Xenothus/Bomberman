package game.blocks;

import static game.main.Config.CLEAR;

public class Clear extends Block
{
    public Clear()
    {
        species = CLEAR;

        walkable = true;
        destroyable = true;
    }
}
