package game.blocks;

import game.auxiliary.Position;
import static game.main.Config.FLAME;

public class Flame extends Block
{
    public Flame(Position position)
    {
        this.position = position;

        species = FLAME;

        walkable = true;
        destroyable = true;
    }
}