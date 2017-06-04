package game.blocks;

import game.main.World;
import game.auxiliary.Position;

import java.util.Date;

import static game.main.Config.*;

/**
 * Created by User on 04.04.2017.
 */
public class Bomb extends Block implements Runnable
{
    private Position position;
    World world;
    private int duration;
    private int blastRadius;
    private boolean existing;

    public Bomb(Position where, World world, int blastRadius)
    {
        position = where;
        this.world = world;
        duration = 2000;
        this.blastRadius = blastRadius;
        existing = true;

        species = BOMB;
        destroyable = true;
    }

    void explode()
    {
        try {
            Thread.sleep(duration);
        }catch(InterruptedException e){}

        existing = false;
        world.explodeBomb(position, blastRadius);
    }

    @Override
    public void run()
    {
        explode();
    }

    public Position getPosition()
    {
        return position;
    }

    public boolean isExisting()
    {
        return existing;
    }
}
