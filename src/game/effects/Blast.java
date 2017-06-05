package game.effects;

import java.util.LinkedList;
import java.util.List;

import game.main.World;
import game.blocks.Block;
import game.blocks.Clear;

import static game.main.Config.FLAME;
import static game.main.Config.BOMB_BLAST_DURATION;

public class Blast implements Runnable
{
    private World world;
    private List<Block> blocks;

    public Blast(World world)
    {
        this.world = world;
        blocks = new LinkedList<>();
    }

    public void add(Block block)
    {
        blocks.add(block);
    }

    @Override
    public void run()
    {
        try {
            Thread.sleep(BOMB_BLAST_DURATION);
        }catch(InterruptedException e){}

        flamesFade();
    }

    private void flamesFade()
    {
        for (Block block : blocks)
        {
            if (block.getSpecies() == FLAME)
                world.actualWorld[block.getPosition().getX()][block.getPosition().getY()] = new Clear();
            else
                world.actualWorld[block.getPosition().getX()][block.getPosition().getY()] = block;
        }
    }
}
