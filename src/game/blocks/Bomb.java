package game.blocks;

import game.main.World;
import game.main.Player;
import game.auxiliary.Position;

import static game.main.Config.BOMB;
import static game.main.Config.BOMB_FIRING_DURATION;

public class Bomb extends Block implements Runnable
{
    private World world;
    private Player player;

    private int blastRadius;
    private boolean existing = true;

    public Bomb(World world, Player player, Position position, int blastRadius)
    {
        this.world = world;
        this.player = player;

        this.position = position;
        this.blastRadius = blastRadius;

        species = BOMB;

        walkable = true;
        destroyable = true;
    }

    @Override
    public void run()
    {
        try {
            Thread.sleep(BOMB_FIRING_DURATION);
        }catch(InterruptedException e){}

        explode();
    }

    public void explode()
    {
        if (existing)
        {
            existing = false;
            world.explodeBomb(position, blastRadius);
            player.notifyBombDetonated();
        }
    }
}
