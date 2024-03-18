package Pong ;

import java.awt.event.KeyEvent ;
import java.awt.event.KeyListener ;

public class Input implements KeyListener {

    @SuppressWarnings("rawtypes")
    public Game game ;
    public Menu menu ;

    // MÉTODO CONSTRUTOR
    public Input(@SuppressWarnings("rawtypes") Game game)
    {
        this.game = game ;
    }

    public Input(Menu menu)
    {
        this.menu = menu;
    }

    // MÉTODO PARA QUANDO A TECLA É PRESSIONADA
    public void keyPressed(KeyEvent e)
    {
        // Se menu nao foi inicializado
        if (menu == null)
        {
            System.out.println("Menu é nulo !!");
        }
        else
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP :
                    menu.button_up = true ;
                    break ;
                case KeyEvent.VK_DOWN :
                    menu.button_down = true ;
                    break;
                case KeyEvent.VK_ENTER :

                        menu.button_enter = true ;

                case KeyEvent.VK_ESCAPE :
                    if(menu.State == Menu.STATE.LASTGAMES)
                    {
                        menu.State = Menu.STATE.MENU ;
                    }
                    if(menu.State == Menu.STATE.ABOUT)
                    {
                        menu.State = Menu.STATE.MENU ;
                    }
            }
        }

        // Se game nao foi inicializado.
        if (game == null)
        {

        }
        else if(game != null)
        {
            if(game.isOver)
            {
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_ESCAPE :
                        String state = "Menu";
                        String name = "Pong2D -> MENU";
                        Window window = new Window(state, name) ;

                        window.initialize() ;
                        break ;
                }

            }

            switch (e.getKeyCode())
            {
                case KeyEvent.VK_W:
                    game.playerOnePaddle.setSlidingUp(true);
                    break;
                case KeyEvent.VK_S:
                    game.playerOnePaddle.setSlidingDown(true);
                    break;
                case KeyEvent.VK_ESCAPE :
                    if (game.isPause == false)
                    {
                        game.isPause(true) ;
                        break ;
                    }else{
                        game.isPause(false) ;
                        break ;
                    }

                case KeyEvent.VK_Q :
                    if(game.isPause == true)
                        System.out.println("Saindo do Jogo ...") ;
                        System.exit(1) ;

                case KeyEvent.VK_R :
                    if(game.isPause || game.isOver )
                    {
                        System.out.println("Reiniciando o Jogo ...") ;
                        game.reinitialize() ;
                    }
            }
        }

    }

    // MÉTODO PARA QUANDO A TECLA É LIBERADA
    public void keyReleased(KeyEvent e)
    {
        if(menu == null)
        {
            //return ;
        }else{
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP :
                    menu.button_up = false ;
                    break ;
                case KeyEvent.VK_DOWN :
                    menu.button_down = false ;
                    break ;
                case KeyEvent.VK_ENTER :
                    menu.button_enter = false ;
                default :
                    break ;
            }
        }

        if(game == null)
        {

        }else{
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_W :
                    game.playerOnePaddle.setSlidingUp(false) ;
                    break ;
                case KeyEvent.VK_S :
                    game.playerOnePaddle.setSlidingDown(false) ;
                    break ;
                default :
                    break ;
            }
        }

    }

    public void keyTyped(KeyEvent e)
    {

    }
}
