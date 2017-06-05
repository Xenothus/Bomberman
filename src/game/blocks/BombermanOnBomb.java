package game.blocks;

import static game.main.Config.BOMBERMEN_ON_BOMB;

public class BombermanOnBomb extends Block
{
    private Bomberman bomberman;
    private Bomb bomb;

    public BombermanOnBomb(Bomberman bomberman, Bomb bomb)
    {
        this.bomberman = bomberman;
        this.bomb = bomb;

        species = BOMBERMEN_ON_BOMB[bomberman.getPlayerID()];

        walkable = false;
        playerOnBomb = true;
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
