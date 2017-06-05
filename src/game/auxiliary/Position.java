package game.auxiliary;

import static game.main.Config.COLS;
import static game.main.Config.ROWS;

public class Position
{
    private int x, y;

    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public boolean isCorrect()
    {
        return !(x < 0 || x >= COLS || y < 0 || y >= ROWS);
    }
}
