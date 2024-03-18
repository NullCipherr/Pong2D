package Pong ;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ball {


    // Ball Forms Variables
    private int x ;
    private int y ;
    private final int RADIUS ;

    // Ball Velocity Variables
    private int xVel ;
    private int yVel ;

    // Game Variables
    private Game game ;

    // Ball Texture
    public BufferedImage ball_Image ;
    public String path_Ball_Image = "/res/Ball/BallImage.png";


    // Método Construtor
    public Ball(int x, int y, int radius, @SuppressWarnings("rawtypes") Game game)
    {
        this.x = x ;
        this.y = y ;
        this.RADIUS = radius ;
        this.game = game ;
        this.xVel = -8 ;
        this.yVel = -8 ;

        initialize() ;
    }

    public void initialize()
    {
        try{
            ball_Image = ImageIO.read(getClass().getResourceAsStream(path_Ball_Image)) ;
        }catch (IOException e)
        {
            e.printStackTrace() ;
        }
    }

    // ATUALIZA A POSIÇÃO DA BOLA E CHECA SE ELA PRECISA RESETAR SUA POSIÇÃO
    public void update()
    {

        if (y - RADIUS <= 0 || y + RADIUS >= game.getHeight())
        {
            Vel_Reverse_y() ;
        }

        if (x - RADIUS <= 0)
        {
            resetPosition() ;
            Vel_Reverse_x() ;
            game.setplayerTwoScore(game.getplayerTwoScore() + 1) ;
        }
        else if (x + RADIUS >= game.getWidth())
        {
            resetPosition() ;
            Vel_Reverse_x() ;
            game.setplayerOneScore(game.getplayerOneScore() + 1) ;
        }

        y += yVel;
        x += xVel;

    }

    //MÉTODO PARA DESENHAR A BOLA NA TELA
    public void draw(Graphics g)
    {
        g.drawImage(ball_Image, x - RADIUS, y - RADIUS, 64, 64 ,null ) ;
    }

    public void render(Graphics g)
    {

    }


    // MÉTODO COM A POSIÇÃO DE 'RESET' DA BOLA .
    private void resetPosition()
    {
        this.x = (game.getWidth() / 2) - RADIUS ;
        this.y = (game.getHeight() / 2) - RADIUS ;
    }


    public boolean collidingPaddle(Paddle paddle)
    {
        if (this.x >= paddle.getX() && this.x <= paddle.getX() + paddle.getWIDTH())
        {
            if (this.y >= paddle.getY() && this.y <= paddle.getY() + paddle.getHEIGHT())
            {
                return true;
            }
        }

        return false;
    }

    //MÉTODO DE VELOCIDADE REVERSA A 'X'
    public void Vel_Reverse_x()
    {
        xVel *= -1 ;
    }

    //MÉTODO DE VELOCIDADE REVERSA A 'Y'
    private void Vel_Reverse_y()
    {
        yVel *= -1 ;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getX()
    {
        return x ;
    }

    public void setxVel(int xVel)
    {
        this.xVel = xVel;
    }

    public int getxVel()
    {
        return xVel ;
    }

    public void setyVel(int yVel)
    {
        this.yVel = yVel ;
    }

    public int getyVel()
    {
        return yVel ;
    }

    public int getRadius()
    {
        return RADIUS ;
    }

    public int getY()
    {
        return y ;
    }
}
