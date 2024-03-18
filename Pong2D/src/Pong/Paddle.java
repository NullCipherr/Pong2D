/*
    ALUNOS :

    Andrei Roberto da Costa
    Gabriel Bitdinger Medeiros
    Felipe Boufleuher da Silva

*/

package Pong ;

import javax.imageio.ImageIO;
import java.awt.* ;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Paddle {

    protected int x ;
    protected int y ;
    protected int yVel = 10 ;

    // CONDITIONALS VARIABLES
    private boolean slidingUp = false ;
    private boolean slidingDown = false ;

    // ELEMENTS SIZE VARIABLES
    private final int WIDTH = 20 ; // PADDLE WIDHT
    protected final int HEIGHT = 200 ; // PADDLE HEIGHT
    public static final int LSIDE = 25 ; // PADDLE 1, LEFT DISTANCE BETWEEN BORDER
    public static final int RSIDE = Game.WIDTH - 115 ; // PADDLE 2, RIGHT DISTANCE BETWEEN BORDER

    //Paddle Texture
    public BufferedImage paddle_1_Image ;

    // Path Paddle Texture
    public String path_Paddle_1_Image = "/res/Paddle/Paddle_1.png" ;
    public String path_Paddle_2_Image = "/res/Paddle/Paddle_2.png" ;

    // MÉTODO CONSTRUTOR
    public Paddle(int x, int paddle)
    {
        this.x = x ;
        this.y = (Game.HEIGHT / 2) - (HEIGHT / 2) ;

        initialize(paddle) ;
    }

    public void initialize(int paddle)
    {
        if(paddle == 1)
        {
            try{
                paddle_1_Image = ImageIO.read(getClass().getResourceAsStream(path_Paddle_1_Image)) ;
            }catch (IOException e)
            {
                e.printStackTrace() ;
            }
        }
        else if(paddle == 2)
        {
            try{
                paddle_1_Image = ImageIO.read(getClass().getResourceAsStream(path_Paddle_2_Image)) ;
            }catch (IOException e)
            {
                e.printStackTrace() ;
            }
        }
    }

    public void update()
    {
        if (slidingUp)
        {
            moveUp() ;
        }

        if (slidingDown)
        {
            moveDown() ;
        }

        lossLimit() ;
    }

    public void draw(Graphics g)
    {
        g.drawImage(paddle_1_Image, x, y, 80, 150, null) ;
    }

    private void moveUp()
    {
        y -= yVel ;
    }

    private void moveDown()
    {
        y += yVel ;
    }

    // MÉTODO PARA QUANDO O PADDLE ESTÁ FORA DOS LIMITES
    protected void lossLimit()
    {
        if (y <= 0)
        {
            y = 0;
        }
        else if (y + HEIGHT >= Game.COMPONENT_HEIGHT)
        {
            y = Game.COMPONENT_HEIGHT - HEIGHT;
        }
    }

    public void setSlidingUp(boolean slidingUp)
    {
        this.slidingUp = slidingUp ;
    }

    public void setSlidingDown(boolean slidingDown)
    {
        this.slidingDown = slidingDown ;
    }

    public int getX()
    {
        return x ;
    }

    public void setX(int x)
    {
        this.x = x ;
    }

    public float getY()
    {
        return y ;
    }

    public void setY(int y)
    {
        this.y = y ;
    }

    public int getWIDTH()
    {
        return WIDTH ;
    }

    public int getHEIGHT()
    {
        return HEIGHT ;
    }
}
