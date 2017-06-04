package game.blocks;

import static game.main.Config.*;

/**
 * Created by Oem on 2017-06-04.
 */
public class BombermanOnBomb extends Block
{
    private Bomberman bomberman;
    private Bomb bomb;

    public BombermanOnBomb(Bomberman bomberman, Bomb bomb)
    {
        this.bomberman = bomberman;
        this.bomb = bomb;

        species = BOMBERMAN_ON_BOMB;
    }

    public Bomberman getBomberman()
    {
        return bomberman;
    }

    public Bomb getBomb()
    {
        return bomb;
    }
}
