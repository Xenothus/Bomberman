package game.blocks;

import static game.main.Config.WOOD;

public class Wood extends Block
{
    public Wood()
    {
        species = WOOD;

        walkable = false;
        destroyable = true;
    }
}
