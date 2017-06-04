package game.main;

import game.blocks.Block;
import game.blocks.Brick;
import game.blocks.Clear;
import game.blocks.Wood;
import game.items.Bomb;
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
        if (!bombs.isEmpty())
            return;

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
        int[][] pattern = new int[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++)pattern[i][j] = 0;
        }

        Position bombPos = pos;
        Position srodek = new Position(bombPos.getX(), bombPos.getY());
        Position gora = new Position(bombPos.getX() , bombPos.getY()-1);
        Position lewo = new Position(bombPos.getX() - 1, bombPos.getY() );
        Position prawo = new Position(bombPos.getX() + 1, bombPos.getY());
        Position dol = new Position(bombPos.getX() , bombPos.getY() + 1);

        if (actualWorld[srodek.getX()][srodek.getY()] == null ||
            actualWorld[srodek.getX()][srodek.getY()].isDestroyable())
        {
            pattern[1][1] = 1;
            if (actualWorld[srodek.getX()][srodek.getY()].isPlayer())
                players.get(findPlayerIndexWithID(actualWorld[srodek.getX()][srodek.getY()].getPlayerID())).die();

            actualWorld[srodek.getX()][srodek.getY()] = new Clear();
        }

        if (gora.getY() >=0 &&
                (actualWorld[gora.getX()][gora.getY()] == null ||
                 actualWorld[gora.getX()][gora.getY()].isDestroyable()))
        {
            pattern[1][0] = 1;
            if (actualWorld[gora.getX()][gora.getY()].isPlayer())
                players.get(findPlayerIndexWithID(actualWorld[gora.getX()][gora.getY()].getPlayerID())).die();

            actualWorld[gora.getX()][gora.getY()] = new Clear();
        }


        if (lewo.getX() >=0 &&
                (actualWorld[lewo.getX()][lewo.getY()] == null ||
                 actualWorld[lewo.getX()][lewo.getY()].isDestroyable()))
        {
            pattern[0][1] = 1;
            if (actualWorld[lewo.getX()][lewo.getY()].isPlayer())
                players.get(findPlayerIndexWithID(actualWorld[lewo.getX()][lewo.getY()].getPlayerID())).die();

            actualWorld[lewo.getX()][lewo.getY()] = new Clear();
        }


        if (prawo.getX() >=0 &&
                (actualWorld[prawo.getX()][prawo.getY()] == null ||
                 actualWorld[prawo.getX()][prawo.getY()].isDestroyable()))
        {
            pattern[2][1] = 1;
            if (actualWorld[prawo.getX()][prawo.getY()].isPlayer())
                players.get(findPlayerIndexWithID(actualWorld[prawo.getX()][prawo.getY()].getPlayerID())).die();

            actualWorld[prawo.getX()][prawo.getY()] = new Clear();
        }


        if (dol.getY() >=0 &&
                (actualWorld[dol.getX()][dol.getY()] == null ||
                 actualWorld[dol.getX()][dol.getY()].isDestroyable()))
        {
            pattern[1][2] = 1;
            if (actualWorld[dol.getX()][dol.getY()].isPlayer())
                players.get(findPlayerIndexWithID(actualWorld[dol.getX()][dol.getY()].getPlayerID())).die();

            actualWorld[dol.getX()][dol.getY()] = new Clear();
        }


        Flame flame = new Flame(srodek,this,pattern);
        try {
            Thread.sleep(10);
        }catch(InterruptedException e){}
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
        for(int i = 0; i< COLS;i++){
            for(int j = 0; j< ROWS;j++){
                if(actualWorld[i][j] != null)
                viewModel[i][j] = actualWorld[i][j].getSpecies();
            }
        }
        for (Iterator<Bomb> it = bombs.iterator(); it.hasNext(); ) {
            Bomb bomb = it.next();
            if (!bomb.isExisting()) {
                it.remove();
            }else{
                if (viewModel[bomb.getPosition().getX()][bomb.getPosition().getY()]==CLEAR)
                    viewModel[bomb.getPosition().getX()][bomb.getPosition().getY()] = BOMB;
            }
        }
        for (Iterator<Flame> it = flames.iterator(); it.hasNext(); ) {
            Flame flame = it.next();
            if(flame != null) {
                if (!flame.isExisting()) {
                    it.remove();
                } else {

                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < 3; k++) {
                            if (flame.getPattern()[j][k] == 1)
                                if(viewModel[flame.getPosition().getX() + j - 1][flame.getPosition().getY() + k - 1]==CLEAR)
                                viewModel[flame.getPosition().getX() + j - 1][flame.getPosition().getY() + k - 1] = FLAME;
                        }
                    }
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
