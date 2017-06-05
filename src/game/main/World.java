package game.main;

import game.blocks.*;
import game.effects.Blast;
import game.blocks.Flame;
import game.auxiliary.Position;

import java.util.ArrayList;
import java.util.List;

import static game.main.Config.*;

/**
 * Created by User on 04.04.2017.
 */
public class World
{
    private static class SingletonHelper {
        private static final World instance = new World();
    }

    public Block[][] actualWorld;
    Player[] players = new Player[MAX_PLAYERS_COUNT];
    List<Flame> flames;

    public synchronized static World getInstance()
    {
        return SingletonHelper.instance;
    }

    private World()
    {
        this.actualWorld = new Block[COLS][ROWS];
        for (int i = 0; i < MAX_PLAYERS_COUNT; i++)
            players[i] = null;
        this.flames = new ArrayList<>();

        byte[][] map = getDefaultWorldMap();

        for(int i = 0; i < COLS; i++)
        {
            for(int j = 0; j < ROWS; j++)
            {
                if (i == 0 || i == COLS - 1 || j == 0 || j == ROWS - 1)
                    actualWorld[i][j] = new Brick();
                else
                    actualWorld[i][j] = new Clear();
            }
        }

        for(int i = 0; i < COLS; i++)
        {
            for (int j = 0; j < ROWS; j++)
            {
                if (map[i][j] == WOOD)
                    actualWorld[i][j] = new Wood();
                if (map[i][j] == BRICK)
                    actualWorld[i][j] = new Brick();
                if (map[i][j] == WOOD_WITH_EXTRA_BOMB)
                    actualWorld[i][j] = new WoodWithExtraBomb();
                if (map[i][j] == WOOD_WITH_EXTRA_GUNPOWDER)
                    actualWorld[i][j] = new WoodWithExtraGunpowder();
            }
        }
    }

    private byte[][] getDefaultWorldMap()
    {
        byte[][] map = new byte[COLS][ROWS];
        for(int i = 2; i < COLS - 1; i += 3)
            for (int j = 2; j < ROWS - 1; j += 3)
                map[i][j] = WOOD;
        for(int i = 3; i < COLS - 1; i += 3)
            for (int j = 2; j < ROWS - 1; j += 6)
                map[i][j] = WOOD;
        for(int i = 4; i < COLS - 1; i += 3)
            for (int j = 2; j < ROWS - 1; j += 3)
                map[i][j] = BRICK;

        map[2][2] = WOOD_WITH_EXTRA_BOMB;
        map[3][2] = WOOD_WITH_EXTRA_GUNPOWDER;

        return map;
    }

    public synchronized void joinPlayerWithID(int ID)
    {
        players[ID] = new Player(this, ID);
        System.out.println("Player " + PLAYER_NAMES[ID] + " joined game");
    }

    public synchronized void quitPlayerWithID(int ID)
    {
        Player player = players[ID];
        Block block = actualWorld[players[ID].position.getX()][players[ID].position.getY()];

        boolean bombermanDelete = false;
        if (block.isPlayer())
            if (((Bomberman)block).getPlayerID() == player.ID)
                bombermanDelete = true;
        else if (block.isPlayerOnBomb())
            if (((BombermanOnBomb)block).getBomberman().getPlayerID() == player.ID)
                bombermanDelete = true;

        if (bombermanDelete)
        {
            actualWorld[players[ID].position.getX()][players[ID].position.getY()] = new Clear();
            players[ID] = null;
        }

        System.out.println("Player " + PLAYER_NAMES[ID] + " left game");
    }

    public synchronized void executePlayerCommand(int ID, byte command)
    {
        players[ID].performAction(command);
    }

    public synchronized void placeBomb(Bomb bomb)
    {
        int x = bomb.getPosition().getX();
        int y = bomb.getPosition().getY();

        try {
            Thread.sleep(10);
        }catch(InterruptedException e){}

        actualWorld[x][y] = new BombermanOnBomb(
                (Bomberman) actualWorld[x][y],
                bomb
        );

        new Thread(bomb).start();
    }

    public synchronized void explodeBomb(Position pos, int blastRadius)
    {
        Blast blast = new Blast(this);

        int x = pos.getX();
        int y = pos.getY();

        //MID
        Flame flame = new Flame(this, new Position(x, y));

        if (actualWorld[x][y].isPlayerOnBomb())
            players[((BombermanOnBomb) actualWorld[x][y]).getBomberman().getPlayerID()].die();

        actualWorld[x][y] = flame;
        blast.add(flame);

        //UP, DOWN, LEFT, RIGHT
        Block currentBlock;
        int Xi, Yi;

        for (int direction = 0; direction < 4; direction++)
        {
            for (int i = 1; i <= blastRadius; i++)
            {
                Xi = x + i * POSITIONS[direction][X];
                Yi = y + i * POSITIONS[direction][Y];

                if (Xi < 0 || Xi >= COLS ||
                    Yi < 0 || Yi >= ROWS)
                    break;

                currentBlock = actualWorld[Xi][Yi];
                flame = new Flame(this, new Position(Xi, Yi));

                if (currentBlock == null || currentBlock.isDestroyable())
                {
                    if (currentBlock.isPlayer())
                    {
                        players[((Bomberman)currentBlock).getPlayerID()].die();
                        actualWorld[Xi][Yi] = flame;
                        blast.add(flame);
                    }
                    else
                    {
                        switch (currentBlock.getSpecies())
                        {
                            case BOMB:
                                ((Bomb) currentBlock).explode();
                                break;

                            case WOOD_WITH_EXTRA_BOMB:
                                actualWorld[Xi][Yi] = flame;
                                blast.add(new ExtraBomb(new Position(Xi, Yi)));
                                break;

                            case WOOD_WITH_EXTRA_GUNPOWDER:
                                actualWorld[Xi][Yi] = flame;
                                blast.add(new ExtraGunpowder(new Position(Xi, Yi)));
                                break;

                            default:
                                actualWorld[Xi][Yi] = flame;
                                blast.add(flame);
                                break;
                        }
                    }
                }
                else break;
            }
        }

        new Thread(blast).start();
    }

    public synchronized byte[][] getViewModel()
    {
        byte [][] viewModel = new byte[COLS][ROWS];
        for(int i = 0; i < COLS; i++)
        {
            for(int j = 0; j < ROWS; j++)
            {
                if(actualWorld[i][j] != null)
                    viewModel[i][j] = actualWorld[i][j].getSpecies();
            }
        }

        return viewModel;
    }
}
