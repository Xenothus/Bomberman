package game.items;

import game.main.World;
import game.auxiliary.Position;

/**
 * Created by User on 04.04.2017.
 */
public class Flame implements Runnable
{
    private Position position;
    private boolean existing;
    World world;
    private int[][] pattern;

    @Override
    public void run()
    {
        try {
            Thread.sleep(1000);
            this.existing = false;
        }catch(InterruptedException e){
        }

        System.out.println("END");
        this.existing = false;
    }

    public Flame(Position pos, World world, int[][] pattern)
    {
        position = pos;
        this.world = world;
        existing = true;
        this.pattern = pattern;
    }

    public Position getPosition()
    {
        return position;
    }

    public boolean isExisting()
    {
        return existing;
    }

    public int[][] getPattern()
    {
        return pattern;
    }
}
