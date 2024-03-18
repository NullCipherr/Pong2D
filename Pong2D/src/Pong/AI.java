package Pong ;

public class AI extends Paddle {

    private Ball ball ;

    // MÉTODO CONSTRUTOR
    public AI(int x, Ball ball, int paddle)
    {
        super(x, paddle) ;
        this.ball = ball ;
        this.yVel = 5 ;
    }

    public void initialize()
    {

    }


    public void update()
    {
        if (ball.getxVel() == 8 && ball.getY() >= y + (HEIGHT / 2))
        {
            y = y + yVel ;
        }

        if (ball.getxVel() == 8 && ball.getY() <= y + (HEIGHT / 2))
        {
            y = y - yVel;
        }

        lossLimit() ;
    }
}
