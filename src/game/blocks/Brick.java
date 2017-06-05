package game.blocks;

import static game.main.Config.BRICK;

public class Brick extends Block
{
    public Brick()
    {
        species = BRICK;

        walkable = false;
        destroyable = false;
    }
}
