package game.blocks;

import game.main.World;
import game.auxiliary.Position;

import static game.main.Config.FLAME;

public class Flame extends Block
{
    private World world;

    public Flame(World world, Position position)
    {
        this.world = world;
        this.position = position;

        species = FLAME;

        walkable = true;
        destroyable = true;
    }
}
