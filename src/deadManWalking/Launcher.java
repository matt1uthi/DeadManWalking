package deadManWalking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *Responsible for one thing, to start up the game,
 * main method that program calls to begin program
 * @author matthewluthi
 */
public class Launcher {
    public static void main(String[] args) {
 
        //Add button to pause the game????
        Game game = new Game("Dead Man Walking", 630, 360); //630, 360
        //Begin thread on own thread
        game.start();
        
        
    }
    
}
