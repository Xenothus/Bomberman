package game.main;

import game.auxiliary.Position;
import game.blocks.Bomb;
import game.blocks.Bomberman;
import game.blocks.Clear;
import static game.main.Config.*;

public class Player
{
    int ID;
    Position position;

    private World world;
    private boolean isAlive;

    private int bombsCount;
    private int bombBlastRadius;

    private long lastMovementTime;

    Player(World world, int ID)
    {
        this.ID = ID;
        this.world = world;

        isAlive = true;
        bombsCount = 1;
        bombBlastRadius = 1;
        lastMovementTime = 0;
        position = PLAYERS_INITIAL_POSITIONS[ID];

        world.set(new Bomberman(ID), position);
    }

    public void notifyBombDetonated()
    {
        bombsCount++;
    }

    void die()
    {
        isAlive = false;
        System.out.println("Player " + PLAYER_NAMES[ID] + " died");
    }

    void performAction(byte command)
    {
        if (!isAlive)
            return;

        if (command == PLANT_BOMB)
        {
            plantBomb();
            return;
        }

        if (PLAYER_MOVEMENT_COOLDOWN_ENABLED)
        {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastMovementTime >= PLAYER_MOVEMENT_COOLDOWN)
                lastMovementTime = currentTime;
            else return;
        }

        Position current = position;
        Position destination = new Position(
                position.getX() + POSITIONS[command][X],
                position.getY() + POSITIONS[command][Y]);

        if (!destination.isCorrect())
            return;

        if (world.get(destination).isWalkable())
        {
            if (world.get(current).isPlayerOnBomb()) {
                world.playerLeavePlantedBomb(ID, current, destination);
            } else if (world.get(destination).getSpecies() == FLAME) {
                die();
                world.set(new Clear(), current);
            } else if (world.get(destination).getSpecies() == EXTRA_BOMB) {
                addBomb();
                world.playerSimpleMove(ID, current, destination);
            } else if (world.get(destination).getSpecies() == EXTRA_GUNPOWDER) {
                addBlastRadius();
                world.playerSimpleMove(ID, current, destination);
            } else
                world.playerSimpleMove(ID, current, destination);

            position = destination;
        }
    }

    private void plantBomb()
    {
        if (bombsCount == 0)
            return;

        if (world.get(position).isPlayerOnBomb())
            return;

        bombsCount--;
        Bomb bomb = new Bomb(world, this,
                new Position(position.getX(), position.getY()), bombBlastRadius);
        world.plantBomb(bomb);

        lastMovementTime = System.currentTimeMillis();
    }

    private void addBomb()
    {
        bombsCount++;
    }

    private void addBlastRadius()
    {
        bombBlastRadius++;
    }
}
