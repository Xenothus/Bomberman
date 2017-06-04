package game.main;

import game.auxiliary.Position;
import game.blocks.Bomb;
import game.blocks.Bomberman;
import game.blocks.BombermanOnBomb;
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

    public void addBlastRadius()
    {
        bombBlastRadius++;
    }

    public void performAction(byte command)
    {
        if (!isAlive)
            return;

        int x = position.getX();
        int y = position.getY();

        Position destination;

        switch(command)
        {
            case MOVE_UP:
                if (x - 1 >= 0 && world.actualWorld[x][y - 1].isWalkable())
                {
                    destination = new Position(x, y - 1);

                    if (world.actualWorld[x][y].getSpecies() == BOMBERMAN_ON_BOMB)
                        getOutOfBomb(position, destination);
                    else if (world.actualWorld[x][y - 1].getSpecies() == BOMB)
                        standOnBomb(position, destination);
                    else if (world.actualWorld[x][y - 1].getSpecies() == EXTRA_BOMB)
                    {
                        addBomb();
                        simpleMove(position, destination);
                    }
                    else if (world.actualWorld[x][y - 1].getSpecies() == EXTRA_GUNPOWDER)
                    {
                        addBlastRadius();
                        simpleMove(position, destination);
                    }
                    else
                        simpleMove(position, destination);
                }
                break;

            case MOVE_LEFT:
                if (x - 1 >= 0 && world.actualWorld[x - 1][y].isWalkable())
                {
                    destination = new Position(x - 1, y);

                    if (world.actualWorld[x][y].getSpecies() == BOMBERMAN_ON_BOMB)
                        getOutOfBomb(position, destination);
                    else if (world.actualWorld[x - 1][y].getSpecies() == BOMB)
                        standOnBomb(position, destination);
                    else if (world.actualWorld[x - 1][y].getSpecies() == EXTRA_BOMB)
                    {
                        addBomb();
                        simpleMove(position, destination);
                    }
                    else if (world.actualWorld[x - 1][y].getSpecies() == EXTRA_GUNPOWDER)
                    {
                        addBlastRadius();
                        simpleMove(position, destination);
                    }
                    else
                        simpleMove(position, destination);
                }
                break;

            case MOVE_DOWN:
                if (y + 1 < ROWS && world.actualWorld[x][y + 1].isWalkable())
                {
                    destination = new Position(x, y + 1);

                    if (world.actualWorld[x][y].getSpecies() == BOMBERMAN_ON_BOMB)
                        getOutOfBomb(position, destination);
                    else if (world.actualWorld[x][y + 1].getSpecies() == BOMB)
                        standOnBomb(position, destination);
                    else if (world.actualWorld[x][y + 1].getSpecies() == EXTRA_BOMB)
                    {
                        addBomb();
                        simpleMove(position, destination);
                    }
                    else if (world.actualWorld[x][y + 1].getSpecies() == EXTRA_GUNPOWDER)
                    {
                        addBlastRadius();
                        simpleMove(position, destination);
                    }
                    else
                        simpleMove(position, destination);
                }
                break;

            case MOVE_RIGHT:
                if (x + 1 < COLS && world.actualWorld[x + 1][y].isWalkable())
                {
                    destination = new Position(x + 1, y);

                    if (world.actualWorld[x][y].getSpecies() == BOMBERMAN_ON_BOMB)
                        getOutOfBomb(position, destination);
                    else if (world.actualWorld[x + 1][y].getSpecies() == BOMB)
                        standOnBomb(position, destination);
                    else if (world.actualWorld[x + 1][y].getSpecies() == EXTRA_BOMB)
                    {
                        addBomb();
                        simpleMove(position, destination);
                    }
                    else if (world.actualWorld[x + 1][y].getSpecies() == EXTRA_GUNPOWDER)
                    {
                        addBlastRadius();
                        simpleMove(position, destination);
                    }
                    else
                        simpleMove(position, destination);
                }
                break;

            case PLANT_BOMB:
                placeBomb();
                break;
        }
    }

    private void simpleMove(Position current, Position destination)
    {
        world.actualWorld[current.getX()][current.getY()] = new Clear();
        world.actualWorld[destination.getX()][destination.getY()] = new Bomberman(ID);
        position = destination;
    }

    private void getOutOfBomb(Position current, Position destination)
    {
        world.actualWorld[current.getX()][current.getY()] =
                ((BombermanOnBomb) world.actualWorld[current.getX()][current.getY()]).getBomb();

        world.actualWorld[destination.getX()][destination.getY()] = new Bomberman(ID);
        position = destination;
    }

    private void standOnBomb(Position current, Position destination)
    {
        BombermanOnBomb bob = new BombermanOnBomb(
                (Bomberman) world.actualWorld[current.getX()][current.getY()],
                (Bomb) world.actualWorld[destination.getX()][destination.getY()]
        );

        world.actualWorld[current.getX()][current.getY()] = new Clear();
        world.actualWorld[destination.getX()][destination.getY()] = new Clear();

        world.actualWorld[destination.getX()][destination.getY()] = bob;

        position = destination;
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
