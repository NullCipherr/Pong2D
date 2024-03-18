import Pong.Window ;

public class Start {

    public static void main(String[] args)
    {
        String state = "Menu" ;
        String name = "Pong2D -> MENU";
        Window window = new Window(state, name) ;

        window.initialize() ;
    }
}