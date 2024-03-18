package Pong ;

import javax.swing.* ;

public class Window {

    @SuppressWarnings("rawtypes")
    public Game game = new Game() ;
    public Menu menu = new Menu() ;
    public JFrame window = new JFrame() ;

    protected enum STATE{
        MENU,
        GAME,
        ABOUT,
        LASTGAMES,
    } ;
    public STATE State ;

    public Window(String state, String name)
    {

        if(state == "Menu")
        {
            State = STATE.MENU ;
            System.out.println("Window ->" + State) ;
            window.add(menu) ;
        }
        if(state == "Game")
        {
            State = STATE.GAME ;
            System.out.println("Window ->" + State);
            window.add(game) ;
        }
        if(state == "Last Games")
        {
            State = STATE.LASTGAMES ;
            System.out.println("Window ->" + State) ;
            window.add(menu) ;
        }
        if(state == "About")
        {
            State = STATE.ABOUT ;
            window.add(menu) ;
        }

        //System.out.println("GAME STATE ->" + state + "-> PASSANDO INFORMAÇÃO PARA ->" + State) ;

        window.pack() ;

        window.setLocationRelativeTo(null) ;
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        window.setVisible(true) ;
        window.setResizable(false) ;
    }


    public void initialize()
    {
        if(State == STATE.MENU)
        {
            System.out.println("Inicializando o Menu !!") ;
            menu.start() ;
        }
        if(State == STATE.GAME)
        {
            System.out.println("Inicializando o Game !!") ;
            game.start() ;
        }
        if(State == STATE.ABOUT)
        {
            System.out.println("Inicializando About ...") ;
            menu.start() ;
        }
        if(State == STATE.LASTGAMES)
        {
            System.out.println("Inicializando Last Games ...") ;
            menu.start() ;
        }
    }


}
