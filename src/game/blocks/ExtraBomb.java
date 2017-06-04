package game.blocks;

import static game.main.Config.*;

/**
 * Created by Oem on 2017-06-04.
 */
public class ExtraBomb extends Block
{
    public ExtraBomb()
    {
        walkable = true;
        destroyable = true;
        species = EXTRA_BOMB;
    }
}
