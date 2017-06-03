package game.blocks;

import static game.main.Config.WOOD;

/**
 * Created by User on 04.04.2017.
 */
public class Wood extends Block
{
    public Wood()
    {
        species = WOOD;
        walkable = false;
        destroyable = true;
    }
}
