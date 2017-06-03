package game.blocks;

import static game.main.Config.BRICK;

/**
 * Created by User on 04.04.2017.
 */
public class Brick extends Block
{
    public Brick()
    {
        walkable = false;
        species = BRICK;
        destroyable = false;
    }
}
