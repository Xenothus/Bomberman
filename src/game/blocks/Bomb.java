package game.blocks;

import game.main.World;
import game.auxiliary.Position;

import static game.main.Config.*;

import game.main.Player;


/**
 * Created by User on 04.04.2017.
 */
public class Bomb extends Block implements Runnable
{
    private World world;
    private Player player;
    private Position position;
    private int blastRadius;

    private final int duration = 2000;
    private boolean existing = true;

    public Bomb(World world, Player player, Position position, int blastRadius)
    {
        this.world = world;
        this.player = player;

        this.position = position;
        this.blastRadius = blastRadius;

        walkable = true;
        species = BOMB;
        destroyable = true;
    }

    @Override
    public void run()
    {
        try {
            Thread.sleep(duration);
        }catch(InterruptedException e){}

        explode();
    }

    public void explode()
    {
        if (existing)
        {
            existing = false;
            world.explodeBomb(position, blastRadius);
            player.myBombIsDetonated();
        }
    }

    public Position getPosition()
    {
        return position;
    }
}
