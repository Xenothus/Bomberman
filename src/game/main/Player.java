package game.main;

import game.auxiliary.Position;
import game.blocks.Bomb;
import game.blocks.Bomberman;
import game.blocks.Clear;

import static game.main.Config.*;

/**
 * Created by User on 04.04.2017.
 */
public class Player
{
    public int ID;
    public Position position;

    private World world;
    private boolean isAlive;

    private int bombsCount;
    private int bombBlastRadius;

    public Player(World world, int ID)
    {
        this.ID = ID;
        this.world = world;

        isAlive = true;
        bombsCount = 1;
        bombBlastRadius = 1;
        position = PLAYERS_INITIAL_POSITIONS[ID];

        world.actualWorld[position.getX()][position.getY()] = new Bomberman(ID);
        System.out.println("Player " + ID + " joined game");
    }

    public void die()
    {
        isAlive = false;
        System.out.println("Player " + ID + " died");
    }

    public void notifyBombDetonated()
    {
        bombsCount++;
    }

    public void addBomb()
    {
        bombsCount++;
    }

    public void performAction(byte command)
    {
        if (!isAlive)
            return;

        int x = position.getX();
        int y = position.getY();

        switch(command)
        {
            case MOVE_UP:
                if (x - 1 >= 0 && world.actualWorld[x][y - 1].isWalkable())
                {
                    moveOut();
                    position.setY(y - 1);
                    moveIn();
                }
                break;

            case MOVE_LEFT:
                if (x - 1 >= 0 && world.actualWorld[x - 1][y].isWalkable())
                {
                    moveOut();
                    position.setX(x - 1);
                    moveIn();
                }
                break;

            case MOVE_DOWN:
                if (y + 1 < ROWS && world.actualWorld[x][y + 1].isWalkable())
                {
                    moveOut();
                    position.setY(y + 1);
                    moveIn();
                }
                break;

            case MOVE_RIGHT:
                if (x + 1 < COLS && world.actualWorld[x + 1][y].isWalkable())
                {
                    moveOut();
                    position.setX(x + 1);
                    moveIn();
                }
                break;

            case PLANT_BOMB:
                placeBomb();
                break;
        }
    }

    private void moveOut()
    {
        if (world.actualWorld[position.getX()][position.getY()].getSpecies() == BOMB)
            System.out.println("HELLO!!!");
        else
            world.actualWorld[position.getX()][position.getY()] = new Clear();
    }

    private void moveIn()
    {
        if (world.actualWorld[position.getX()][position.getY()].getSpecies() == BOMB)
            System.out.println("OH MAH GAHD!!!");
        else
            world.actualWorld[position.getX()][position.getY()] = new Bomberman(ID);
    }

    private void placeBomb()
    {
        if (bombsCount == 0)
            return;

        bombsCount--;
        Bomb bomb = new Bomb(world, this,
                new Position(position.getX(), position.getY()), bombBlastRadius);
        world.placeBomb(bomb);
    }
}
