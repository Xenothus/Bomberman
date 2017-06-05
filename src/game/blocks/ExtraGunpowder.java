package game.blocks;

import game.auxiliary.Position;

import static game.main.Config.EXTRA_GUNPOWDER;

public class ExtraGunpowder extends Block
{
    public ExtraGunpowder(Position position)
    {
        this.position = position;

        species = EXTRA_GUNPOWDER;

        walkable = true;
        destroyable = true;
    }
}
