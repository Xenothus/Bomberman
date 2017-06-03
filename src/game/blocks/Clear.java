package game.blocks;

import static game.main.Config.CLEAR;

/**
 * Created by Patryk on 05.04.2017.
 */
public class Clear extends Block
{
    public Clear()
    {
        walkable = true;
        species = CLEAR;
        destroyable = true;
    }
}
