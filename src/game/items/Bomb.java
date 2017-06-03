package game.items;

import game.main.World;
import game.auxiliary.Position;

import java.util.Date;

/**
 * Created by User on 04.04.2017.
 */
public class Bomb implements Runnable
{
    private Position position;
    private int timer;
    World world;
    private boolean existing;

    public Bomb(Position where, World world)
    {
        existing = true;
        position = where;
        this.world = world;
    }

    void explode()
    {
        Date time = new Date();
        int timer = 2000;
        try {
            Thread.sleep(2000);
        }catch(InterruptedException e){

        }
        existing = false;
        world.explodeBomb(position);
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
