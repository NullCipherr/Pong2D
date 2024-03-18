package Pong ;

import javax.imageio.ImageIO;

import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage ;
import java.awt.image.BufferStrategy ;
import java.io.IOException;
import java.util.Iterator;

public class Menu extends Canvas implements Runnable  {

    @SuppressWarnings("rawtypes")
    Game game = new Game() ;

    // Menu Textures
    public BufferedImage menu_Background ;
    public BufferedImage menu_Image ;
    public BufferedImage menu_Icon ;
    public BufferedImage menu_NG_Image ;
    public BufferedImage menu_R_Image ;
    public BufferedImage menu_A_Image ;
    public BufferedImage menu_Q_Image ;
    public BufferedImage menu_Arrow_Image ;

    //About Textures
    public BufferedImage about_Info_Image ;

    //Ranking Textures
    public BufferedImage ranking_Image ;

    //Menu Textures Path'
    public String path_menu_Icon = "/res/Menu/Logo_Game.png" ;
    public String path_Background_Image = "/res/Background.png" ;
    public String path_menu_Image = "/res/Menu/Menu_Image.png" ;
    public String path_menu_NG_Image = "/res/Menu/New Game.png" ;
    public String path_menu_R_Image = "/res/Menu/Ranking.png" ;
    public String path_menu_A_Image = "/res/Menu/About.png" ;
    public String path_menu_Q_Image = "/res/Menu/Quit.png" ;
    public String path_menu_Arrow_Image = "/res/Menu/Menu_Arrow.png" ;

    //About Textures Path
    public String path_about_Info_Image = "/res/About/About_Info.png" ;

    // Ranking Textures Path
    public String path_ranking_Image = "/res/Menu/Ranking/Ranking_Image.png" ;

    // Menu
    public String[] option = {"New Game","Last Games","About","Quit"};
    public int currentOption = 0 ;
    public int maxOption = option.length-1 ;
    public boolean button_up = false, button_down = false, button_enter ;
    public static boolean pause = false ;
    public static boolean execute = false ;
    BufferedImage image ;
    protected enum STATE{
        MENU,
        GAME,
        ABOUT,
        LASTGAMES,
    } ;
    public STATE State ;

    // Tamanho da Janela
    public String state ;
    public static final int WIDTH = 1280 ;
    public static final int HEIGHT = 720 ;
    public static int COMPONENT_HEIGHT ;

    // THREAD
    private boolean isRunning = false ;
    private Thread menuThread ;
    private BufferStrategy buffer ;
    private final int DOUBLE_BUFFER = 2 ;
    @SuppressWarnings("unused")
    private int FramesPerSecond ;

    public String Teste = "Coloque o resultado aqui" ;

    public Menu()
    {
        Dimension size = new Dimension(WIDTH, HEIGHT) ;
        setPreferredSize(size) ;
        addKeyListener(new Input(this)) ;

        initialize() ;
    }

    public void initialize()
    {
        State = STATE.MENU ;

        if(State == STATE.MENU)
        {
            try{
                menu_Background = ImageIO.read(getClass().getResourceAsStream(path_Background_Image)) ;
                menu_Image = ImageIO.read(getClass().getResourceAsStream(path_menu_Image)) ;
                menu_Arrow_Image = ImageIO.read(getClass().getResourceAsStream(path_menu_Arrow_Image)) ;
                about_Info_Image = ImageIO.read(getClass().getResourceAsStream(path_about_Info_Image)) ;
                ranking_Image = ImageIO.read(getClass().getResourceAsStream(path_ranking_Image)) ;
            }catch (IOException e)
            {
                e.printStackTrace() ;
            }
        }
    }

    public synchronized void start()
    {
        COMPONENT_HEIGHT = getHeight() ;

        if (isRunning)
        {
            return ;
        }

        isRunning = true ;

        menuThread = new Thread(this, "menuThread") ;
        menuThread.start() ;
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
            long currentTime = System.nanoTime() ;
            elapsedTime += (currentTime - pastTime) / TARGET_TIME ;
            pastTime = currentTime;

            while (elapsedTime >= 1)
            {
                update() ;
                elapsedTime-- ;
            }

            if(State == STATE.LASTGAMES)
            {
                renderRanking() ;
                frames++;
            }

            if(State == STATE.ABOUT)
            {
                renderAbout() ;
                frames++ ; // FRAME É SOMADO
           }
            if(State == STATE.MENU){
                render() ;	// RENDERIZA OS GRÁFICOS
                frames++ ; // FRAME É SOMADO
            }


            if (System.currentTimeMillis() - lastMilliSec > 1000)
            {
                lastMilliSec += 1000 ;
                FramesPerSecond = frames ;
                frames = 0 ;
            }
        }
    }


    private void update()
    {
        if(State == STATE.MENU)
        {
            if(button_up)
            {
                button_up = false ;
                currentOption-- ;
                if(currentOption < 0)
                {
                    currentOption = maxOption ;
                }
            }
            if(button_down)
            {
                button_down = false ;
                currentOption++ ;
                if(currentOption > maxOption)
                {
                    currentOption = 0 ;
                }
            }
            // Caso apertamos Enter :
            if(button_enter)
            {
                if (option[currentOption] == "New Game")
                {
                    if (button_enter == true)
                    {
                        System.out.println("Iniciar Jogo !!") ;
                        State = STATE.GAME ;
                        button_enter = false ;
                        Window window = new Window("Game", "Pong2D -> GAME") ;

                        window.initialize() ;

                    }
                }
                if (option[currentOption] == "Last Games")
                {
                    if (button_enter == true)
                    {
                        System.out.println("Inicializando Last Games !!") ;
                        State = STATE.LASTGAMES ;
                        button_enter = false ;
                    }
                }
                if (option[currentOption] == "About")
                {
                    if (button_enter == true)
                    {
                        System.out.println("Inicializando About") ;
                        State = STATE.ABOUT ;
                        button_enter = false ;
                    }
                }
                if (option[currentOption] == "Quit")
                {
                    if (button_enter == true)
                    {
                        System.out.println("Saindo ....") ;
                        System.exit(1) ;
                    }
                }
            }
        }
        if(State == STATE.ABOUT)
        {
            System.out.println("ABOUT") ;
        }
        if(State == STATE.LASTGAMES)
        {
            System.out.println("Last Games") ;
        }
    }

    private void render()
    {

        buffer = getBufferStrategy() ;

        if (buffer == null)
        {
            createBufferStrategy(DOUBLE_BUFFER) ;
            return ;
        }

        Graphics g = buffer.getDrawGraphics() ;

        draw(g) ;


        if (option[currentOption] == "New Game")
        {
            g.drawImage(menu_Arrow_Image, (Game.WIDTH/ 2) - 225, (Game.HEIGHT/ 2 - 90),null) ;
        }
        if (option[currentOption] == "Last Games")
        {
            g.drawImage(menu_Arrow_Image, (Game.WIDTH) / 2 - 225, (Game.HEIGHT/ 2 + 10), null) ;
        }
        if (option[currentOption] == "About")
        {
            g.drawImage(menu_Arrow_Image, (Game.WIDTH) / 2 - 225, (Game.HEIGHT) / 2 + 110, null) ;
        }
        if (option[currentOption] == "Quit")
        {
            g.drawImage(menu_Arrow_Image, (Game.WIDTH) / 2 - 225, (Game.HEIGHT) / 2 + 215, null) ;
        }

        g.dispose() ;
        buffer.show() ;
    }

    private void draw(Graphics g)
    {
        g.drawImage(menu_Background, 0, 0, null) ; // BACKGROUND MENU
        g.drawImage(menu_Image, 0, 0, null) ;
    }

    private void renderAbout()
    {
        buffer = getBufferStrategy() ;

        if (buffer == null)
        {
            createBufferStrategy(DOUBLE_BUFFER) ;
            return ;
        }

        Graphics g = buffer.getDrawGraphics() ;
        g.drawImage(menu_Background, 0, 0, null) ; // BACKGROUND MENU
        g.drawImage(about_Info_Image, 0, 0 , null) ;

        g.dispose() ;
        buffer.show();
    }

    public void renderRanking()
    {
        buffer = getBufferStrategy() ;

        if (buffer == null)
        {
            createBufferStrategy(DOUBLE_BUFFER) ;
            return ;
        }

        Graphics g = buffer.getDrawGraphics() ;
        g.drawImage(menu_Background, 0, 0, null) ; // BACKGROUND MENU
        g.drawImage(ranking_Image, 0 , 0 , null) ;

        g.setFont(new Font("cyberdyne", Font.BOLD, 35)) ;
        g.drawString("TOP 5 BEST SCORES", (Game.WIDTH) / 2 - 175 , (Game.HEIGHT) - 500 ) ;

        List<String> ranking = con.projectScore(); //new List();
        Iterator<String> it = ranking.iterator();
        int height = 450;
        int i = 1;

        while(it.hasNext()) 
        {
            String a = it.next();
            g.setFont(new Font("cyberdyne", Font.BOLD, 20)) ;
            g.drawString(a, (Game.WIDTH) / 2 - 10, (Game.HEIGHT) - height ) ;
            height -= 50;
            i++;
            if(i > 5) break;
        }

        g.dispose() ;
        buffer.show();
    }
}






