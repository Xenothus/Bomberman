package game.main;

import game.auxiliary.Position;
import game.blocks.Bomberman;
import game.blocks.Clear;
import javafx.geometry.Pos;

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
    private char action;

    public Player(World world, int ID)
    {
        this.ID = ID;
        this.world = world;
        isAlive = true;
        action = 0;
        position = PLAYERS_INITIAL_POSITIONS[ID];
        world.actualWorld[position.getX()][position.getY()] = new Bomberman(ID);
    }

    public void die()
    {
        isAlive = false;
        System.out.println("Died");
    }

    void placeBomb(int x, int y)
    {
        world.placeBomb(new Position(x, y));
    }

    public void performAction(byte command)
    {
        int x = position.getX();
        int y = position.getY();

        switch(command)
        {
            case MOVE_UP:
                if (x - 1 >= 0 && world.actualWorld[x][y - 1].isWalkable())
                {
                    world.actualWorld[position.getX()][position.getY()] = new Clear();
                    position.setY(y - 1);
                    world.actualWorld[position.getX()][position.getY()] = new Bomberman(ID);
                }
                break;

            case MOVE_LEFT:
                if (x - 1 >= 0 && world.actualWorld[x - 1][y].isWalkable())
                {
                    world.actualWorld[position.getX()][position.getY()] = new Clear();
                    position.setX(x - 1);
                    world.actualWorld[position.getX()][position.getY()] = new Bomberman(ID);
                }
                break;

            case MOVE_DOWN:
                if (y + 1 < ROWS && world.actualWorld[x][y + 1].isWalkable())
                {
                    world.actualWorld[position.getX()][position.getY()] = new Clear();
                    position.setY(y + 1);
                    world.actualWorld[position.getX()][position.getY()] = new Bomberman(ID);
                }
                break;

            case MOVE_RIGHT:
                if (x + 1 < COLS && world.actualWorld[x + 1][y].isWalkable())
                {
                    world.actualWorld[position.getX()][position.getY()] = new Clear();
                    position.setX(x + 1);
                    world.actualWorld[position.getX()][position.getY()] = new Bomberman(ID);
                }
                break;

            case PLANT_BOMB:
                placeBomb(x, y);
                break;
        }
    }
}
