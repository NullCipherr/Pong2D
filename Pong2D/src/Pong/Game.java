package Pong ;

import java.awt.Canvas ;
import java.awt.Color ;
import java.awt.Dimension ;
import java.awt.Font ;
import java.awt.Graphics ;
import java.awt.image.BufferStrategy ;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Game<roboto> extends Canvas implements Runnable {

    // Tamanho da Janela
    public final int TIME_LIMIT = 30 ;
    public String state ;
    public Window window ;
    public static final int WIDTH = 1280 ;
    public static final int HEIGHT = 720 ;
    public static int COMPONENT_HEIGHT ;


    public boolean isOver ;
    public boolean isPause ;

    public long totalTime = 0 ;
    private boolean isRunning = false ;
    private Thread gameThread ;
    private BufferStrategy buffer ;
    private final int DOUBLE_BUFFER = 2 ;
    @SuppressWarnings("unused")
    private int FramesPerSecond ;


    // Player 1 Variables
    public Paddle playerOnePaddle ;
    private int playerOneScore = 0 ;

    // Player 2 Variables
    public AI playerTwoPaddle ;
    private int playerTwoScore = 0 ;

    // Ball Variables
    private Ball ball ;

    public BufferedImage background ;
    public String path_Background_Image = "/res/Background.png" ;

    public BufferedImage gameOver_Image ;
    public String pathGameOver_Image = "/res/Game/GameOver/GameOver_Image.png" ;

    public BufferedImage scoreboard_Image ;
    public String path_Scoreboard_Image = "/res/Game/Scoreboard.png" ;

    public BufferedImage isPause_Image ;
    public String path_isPaused_Image = "/res/Game/Pause/Paused_Image.png" ;

    public Game()
    {
        Dimension size = new Dimension(WIDTH, HEIGHT) ;
        setPreferredSize(size) ;
        addKeyListener(new Input(this)) ;
        state = "Game" ;

        initialize() ;
    }

    private void initialize()
    {
        ball = new Ball(WIDTH / 2, HEIGHT / 2, 10, this) ; // INICIALIZA A BOLA.
        playerTwoPaddle = new AI(Paddle.RSIDE, ball, 2) ; // INICIALIZA O PADDLE DO PLAYER 1.
        playerOnePaddle = new Paddle(Paddle.LSIDE, 1) ; // INICIALIZA O PADDLE DO PLAYER 1.
        isOver = false ;
        isPause = false ;

        try{
            background = ImageIO.read(getClass().getResourceAsStream(path_Background_Image)) ;
            scoreboard_Image = ImageIO.read(getClass().getResourceAsStream(path_Scoreboard_Image)) ;
            gameOver_Image = ImageIO.read(getClass().getResourceAsStream(pathGameOver_Image)) ;
            isPause_Image = ImageIO.read(getClass().getResourceAsStream(path_isPaused_Image)) ;

        }catch (IOException e)
        {
            e.printStackTrace() ;
        }

    }

    public void reinitialize()
    {
        playerOneScore = 0 ;
        playerTwoScore = 0 ;
        totalTime = 0 ;

        initialize() ;
        start() ;
    }

    public synchronized void start()
    {
        COMPONENT_HEIGHT = getHeight() ;

        if (isRunning)
        {
            return ;
        }

        isRunning = true ;

        gameThread = new Thread(this, "gameThread") ;
        gameThread.start() ;
    }

    public void run()
    {
        long lastMilliSec = System.currentTimeMillis() ;
        long pastTime = System.nanoTime() ;
        final double UPS = 60.0 ;
        final double NANOSEC = 1000000000.0 ;
        final double TARGET_TIME = NANOSEC / UPS ;

        double elapsedTime = 0 ;
        int frames = 0 ;

        while (isRunning)
        {
            if(isOver)
            {         
                while(isOver)
                {
                    long currentTime = System.nanoTime() ;
                    elapsedTime += (currentTime - pastTime) / TARGET_TIME ;
                    pastTime = currentTime ;

                    while (elapsedTime >= 1)
                    {
                        update() ;
                        elapsedTime-- ;
                    }

                    gameOverRender() ;
                    frames++ ;

                    if (System.currentTimeMillis() - lastMilliSec > 1000)
                    {
                        lastMilliSec += 1000 ;
                        FramesPerSecond = frames ;
                        frames = 0 ;
                    }
                }
            }

            if(isPause)
            {
                while(isPause)
                {
                    long currentTime = System.nanoTime() ;
                    elapsedTime += (currentTime - pastTime) / TARGET_TIME ;
                    pastTime = currentTime ;

                    while (elapsedTime >= 1)
                    {
                        update() ;
                        elapsedTime-- ;
                    }

                    isPauseRender();	// RENDERIZA OS GRÁFICOS
                    frames++ ; // FRAME É SOMADO

                    if (System.currentTimeMillis() - lastMilliSec > 1000)
                    {
                        lastMilliSec += 1000 ;
                        FramesPerSecond = frames ;
                        frames = 0 ;
                    }
                }
            }
            if(!isPause && !isOver)
            {
                long currentTime = System.nanoTime() ;
                elapsedTime += (currentTime - pastTime) / TARGET_TIME ;
                pastTime = currentTime ;

                while (elapsedTime >= 1)
                {
                    update() ;
                    elapsedTime-- ;
                }

                render() ;	// RENDERIZA OS GRÁFICOS
                frames++ ; // FRAME É SOMADO

                if (System.currentTimeMillis() - lastMilliSec > 1000)
                {
                    totalTime++ ;
                    lastMilliSec += 1000 ;
                    FramesPerSecond = frames ;
                    frames = 0 ;
                }

                if(totalTime == TIME_LIMIT)
                {
                    isOver = true ;
                }
            }

        }
    }

    private void update()
    {
        if(isOver)
        {
            System.out.println("Game is Over !! ....") ;
        }

        if(isPause)
        {
            System.out.println("Game is Paused ..") ;
        }
        if(!isOver && !isPause)
        {
            System.out.println("Game is Running .. ") ;

            if (ball.collidingPaddle(playerOnePaddle))
            {
                ball.Vel_Reverse_x() ;
                ball.setX(playerOnePaddle.getX() + playerOnePaddle.getWIDTH()) ;
            }
            else if (ball.collidingPaddle(playerTwoPaddle))
            {
                ball.Vel_Reverse_x() ;
                ball.setX(playerTwoPaddle.getX()) ;
            }
            ball.update() ;
            playerOnePaddle.update() ;
            playerTwoPaddle.update() ;
        }
    }

    private void draw(Graphics g)
    {
        g.drawImage(background, 0, 0, null) ;
        g.drawImage(scoreboard_Image, 0, 0, null) ;
    }

    private void render()
    {
        buffer = getBufferStrategy() ;

        if (buffer == null)
        {
            createBufferStrategy(DOUBLE_BUFFER) ;
            return ;
        }

        Graphics graphics = buffer.getDrawGraphics() ;

        draw(graphics) ;

        // Scoreboard
        graphics.setColor(Color.WHITE) ;
        graphics.setFont(new Font("cyberdyne", Font.BOLD, 40)) ;

        /* ---------------------------- Display Player 1 Scoreboard---------------------------- */
        graphics.drawString("" + playerOneScore, (WIDTH / 2) - 385 , 60) ;
        /*--------------------------------------------------------------------------------------*/

        /* ------------------------------------- Timer ----------------------------------------- */
        graphics.drawString("" + totalTime, (WIDTH / 2 - 10)   , 60) ;
        /*--------------------------------------------------------------------------------------*/


        /* ---------------------------- Display Player 2 Scoreboard---------------------------- */
        graphics.drawString("" + playerTwoScore, (WIDTH / 2) + 365 , 60) ;
        /*--------------------------------------------------------------------------------------*/


        graphics.setColor(Color.YELLOW) ;

        // Desenha os elementos 'Raquete' e 'Bola' na Tela .
        ball.draw(graphics) ;
        playerOnePaddle.draw(graphics) ;
        playerTwoPaddle.draw(graphics) ;

        graphics.dispose() ;
        buffer.show() ;

    }

    public void isPauseRender()
    {
        buffer = getBufferStrategy() ;

        if (buffer == null)
        {
            createBufferStrategy(DOUBLE_BUFFER) ;
            return ;
        }

        Graphics graphics = buffer.getDrawGraphics() ;

        graphics.drawImage(background,0,0,null) ;
        graphics.drawImage(isPause_Image, 0, 0, null) ;

        graphics.dispose() ;
        buffer.show() ;
    }

    public void gameOverRender()
    {
        buffer = getBufferStrategy();
        if (buffer == null) {
            createBufferStrategy(DOUBLE_BUFFER);
            return;
        }

        Graphics graphics = buffer.getDrawGraphics();


        graphics.drawImage(background,0,0,null);

        // Scoreboard
        graphics.setColor(Color.WHITE) ;
        graphics.setFont(new Font("cyberdyne", Font.BOLD, 40)) ;

        graphics.drawImage(gameOver_Image,0,0,null) ;
        graphics.drawString("" + getplayerOneScore() , (WIDTH / 2) - 350, (HEIGHT / 2 + 100));
        graphics.drawString("" + getplayerTwoScore() , (WIDTH / 2 + 300), (HEIGHT / 2 + 100));

        graphics.dispose();
        buffer.show() ;

    }

    public synchronized void stop()
    {
        if (!isRunning)
        {
            return ;
        }

        isRunning = false ;

        try
        {
            gameThread.join() ;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace() ;
        }
    }

    public void isPause(boolean pause)
    {
        if(pause)
        {
            this.isPause = pause ;
        }
        else{
            this.isPause = pause ;
        }
    }
    

    public int getplayerOneScore()
    {
        return playerOneScore ;
    }

    public void setplayerOneScore(int playerOneScore)
    {
        this.playerOneScore = playerOneScore ;
    }

    public int getplayerTwoScore()
    {
        return playerTwoScore ;
    }

    public void setplayerTwoScore(int playerTwoScore)
    {
        this.playerTwoScore = playerTwoScore ;
    }

}