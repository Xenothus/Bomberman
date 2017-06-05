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

        if (command == PLANT_BOMB)
        {
            placeBomb();
            return;
        }

        int x = position.getX();
        int y = position.getY();

        int xStep = POSITIONS[command][X];
        int yStep = POSITIONS[command][Y];

        if (x + xStep < 0 || x + xStep >= COLS ||
            y + yStep < 0 || y + yStep >= ROWS)
            return;

        if (world.actualWorld[x + xStep][y + yStep].isWalkable())
        {
            Position destination = new Position(x + xStep, y + yStep);

            if (world.actualWorld[x][y].isPlayerOnBomb())
                getOutOfBomb(position, destination);
            else if (world.actualWorld[x + xStep][y + yStep].getSpecies() == BOMB)
                standOnBomb(position, destination);
            else if (world.actualWorld[x + xStep][y + yStep].getSpecies() == EXTRA_BOMB) {
                addBomb();
                simpleMove(position, destination);
            } else if (world.actualWorld[x + xStep][y + yStep].getSpecies() == EXTRA_GUNPOWDER) {
                addBlastRadius();
                simpleMove(position, destination);
            } else
                simpleMove(position, destination);
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
