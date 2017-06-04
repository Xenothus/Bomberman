package game.main;

import game.blocks.Block;
import game.blocks.Brick;
import game.blocks.Clear;
import game.blocks.Wood;
import game.blocks.Bomb;
import game.items.Flame;
import game.auxiliary.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
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

    List<Player> players;

    Block[][] actualWorld;
    List<Bomb> bombs;
    List<Flame> flames;

    public synchronized static World getInstance()
    {
        return SingletonHelper.instance;
    }

    private World()
    {
        players = new LinkedList<>();

        this.bombs = new ArrayList<>();
        this.flames = new ArrayList<>();
        this.actualWorld = new Block[COLS][ROWS];

        byte[][] map = getDefaultWorldMap();

        for(int i=0;i<COLS;i++)
        {
            for(int j=0;j<ROWS;j++)
            {
                if (i == 0 || i == COLS - 1 || j == 0 || j == ROWS - 1)
                    actualWorld[i][j] = new Brick();
                else
                    actualWorld[i][j] = new Clear();
            }
        }

        for(int i=0;i<COLS;i++)
        {
            for (int j = 0; j < ROWS; j++)
            {
                if (map[i][j] == WOOD) actualWorld[i][j] = new Wood();
                if (map[i][j] == BRICK) actualWorld[i][j] = new Brick();
            }
        }
    }

    public byte[][] getDefaultWorldMap()
    {
        byte[][] map = new byte[COLS][ROWS];
        for(int i = 2; i< COLS-1; i+=3)
            for (int j = 2; j< ROWS-1; j+=3)
                map[i][j]= WOOD;
        for(int i = 3; i< COLS-1; i+=3)
            for (int j = 2; j< ROWS-1; j+=6)
                map[i][j]= WOOD;
        for(int i = 4; i< COLS-1; i+=3)
            for (int j = 2; j< ROWS-1; j+=3)
                map[i][j]= BRICK;

        return map;
    }

    public synchronized void addNewPlayer(int ID)
    {
        players.add(new Player(this, ID));
    }

    public synchronized void executeCommand(int ID, byte command)
    {
        int index = findPlayerIndexWithID(ID);
        if (index == -1)
            return;

        players.get(index).performAction(command);
    }

    private int findPlayerIndexWithID(int ID)
    {
        int i;
        boolean found = false;
        for (i = 0; i < players.size(); i++)
        {
            if (players.get(i).ID == ID)
            {
                found = true;
                break;
            }
        }

        if (!found)
        {
            System.err.println("Could not find player");
            return -1;
        }

        return i;
    }

    public void placeBomb(Position pos, int blastRadius)
    {
//        if (!bombs.isEmpty())
//            return;

        Bomb bomb = new Bomb(pos,this, blastRadius);
        try {
            Thread.sleep(10);
        }catch(InterruptedException e){}
        bombs.add(bomb);
        Thread newThread = new Thread(bomb);
        newThread.start();
    }

    public void explodeBomb(Position pos, int blastRadius)
    {
        System.out.println("OK");
        int[][] pattern = new int[4][blastRadius];
        for (int i = 0; i < pattern.length; i++)
            for (int k = 0; k < pattern[0].length; k++)
                pattern[i][k] = 0;

        int x = pos.getX();
        int y = pos.getY();

        //UP
        Block currentBlock;
        for (int i = 1; i <= blastRadius; i++)
        {
            if (y - i >= 0)
            {
                currentBlock = actualWorld[x][y - i];

                if (currentBlock == null || currentBlock.isDestroyable())
                {
                    pattern[UP][i - 1] = 1;
                    if (currentBlock.isPlayer())
                        players.get(findPlayerIndexWithID(currentBlock.getPlayerID())).die();


                    actualWorld[x][y - i] = new Clear();
                }
                else break;
            }
            else break;
        }

        //LEFT
        for (int i = 1; i <= blastRadius; i++)
        {
            if (x - i >= 0)
            {
                currentBlock = actualWorld[x - i][y];

                if (currentBlock == null || currentBlock.isDestroyable())
                {
                    pattern[LEFT][i - 1] = 1;
                    if (currentBlock.isPlayer())
                        players.get(findPlayerIndexWithID(currentBlock.getPlayerID())).die();

                    actualWorld[x - i][y] = new Clear();
                }
                else break;
            }
            else break;
        }

        //RIGHT
        for (int i = 1; i <= blastRadius; i++)
        {
            if (x + i < COLS)
            {
                currentBlock = actualWorld[x + i][y];

                if (currentBlock == null || currentBlock.isDestroyable())
                {
                    pattern[RIGHT][i - 1] = 1;
                    if (currentBlock.isPlayer())
                        players.get(findPlayerIndexWithID(currentBlock.getPlayerID())).die();

                    actualWorld[x + i][y] = new Clear();
                }
                else break;
            }
            else break;
        }

        //DOWN
        for (int i = 1; i <= blastRadius; i++)
        {
            if (y + i < ROWS)
            {
                currentBlock = actualWorld[x][y + i];

                if (currentBlock == null || currentBlock.isDestroyable())
                {
                    pattern[DOWN][i - 1] = 1;
                    if (currentBlock.isPlayer())
                        players.get(findPlayerIndexWithID(currentBlock.getPlayerID())).die();

                    actualWorld[x][y + i] = new Clear();
                }
                else break;
            }
            else break;
        }

        for (int i = 0; i < 4; i++)
        {
            for (int k = 0; k < pattern[0].length; k++)
            {
                System.out.print(pattern[i][k] + "\t");
            }

            System.out.print("\n");
        }

        Flame flame = new Flame(pos,this, pattern);
/*        try {
            Thread.sleep(10);
        }catch(InterruptedException e){}*/
        flames.add(flame);
        Thread newThread = new Thread(flame);
        newThread.start();
    }

    public void stopFlame(Flame flame)
    {
        flames.remove(flame);
    }

    public synchronized byte[][] getViewTable()
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

        for (Iterator<Bomb> it = bombs.iterator(); it.hasNext();)
        {
            Bomb bomb = it.next();
            if (!bomb.isExisting())
                it.remove();
            else
            {
                if (viewModel[bomb.getPosition().getX()][bomb.getPosition().getY()] == CLEAR)
                    viewModel[bomb.getPosition().getX()][bomb.getPosition().getY()] = BOMB;
            }
        }

        for (Iterator<Flame> it = flames.iterator(); it.hasNext();)
        {
            Flame flame = it.next();
            if(flame != null)
            {
                if (!flame.isExisting())
                    it.remove();
                else
                {
                    int x = flame.getPosition().getX();
                    int y = flame.getPosition().getY();

                    viewModel[x][y] = FLAME;

                    int[][] pattern = flame.getPattern();

                    for (int j = 0; j < pattern.length; j++)            // direction
                    {
                        for (int k = 0; k < pattern[0].length; k++)     // each element
                        {
                            if (pattern[j][k] == 1)
                            {
                                switch (j)
                                {
                                    case UP:
                                        viewModel[x][y - k - 1] = FLAME;
                                        break;

                                    case LEFT:
                                        viewModel[x - k - 1][y] = FLAME;
                                        break;

                                    case RIGHT:
                                        viewModel[x + k + 1][y] = FLAME;
                                        break;

                                    case DOWN:
                                        viewModel[x][y + k + 1] = FLAME;
                                        break;
                                }
                            }
                        }
                    }


/*                    for (int g = 0; g < COLS; g++)
                    {
                        for (int b = 0; b < ROWS; b++)
                        {
                            System.out.print(viewModel[g][b]);
                        }
                        System.out.print("\n");
                    }

                    System.out.print("\n");
                    System.out.print("\n");
                    System.out.print("\n");*/
                }
            }
        }

        //Petla ktora leci po wszystkich playerach
/*        for (Player player : players)
            viewModel[player.position.getX()][player.position.getY()] = BOMBERMAN;*/

        //Zamiast tego bedzie wysylanie do klientow
        //view.updateMap(viewModel);


        //todelete
/*        for (int i = 0; i < COLS; i++)
        {
            for (int k = 0; k < ROWS; k++)
            {
                System.out.print(viewModel[i][k]);
            }
            System.out.print("\n");
        }

        System.out.print("\n");
        System.out.print("\n");*/

        return viewModel;
    }
}
