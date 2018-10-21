package deadManWalking.database;

import deadManWalking.Game;
import deadManWalking.Handler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mxtt Offline
 */
public class DBOperations {

    String url = "jdbc:derby:DeadmanDB;create=true";
    String username = "matt";
    String password = "12345";
    //Allow connection to our database (like a bridge)
    Connection conn = null;
    Statement statement = null;
    
    Handler handler;

    public DBOperations(Handler handler) {
        this.handler = handler;
    }
    
    public void establishConnection() {
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println(url + " connected...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            statement = conn.createStatement();
            String newTable = "Leaderboard";
            
            String sqlDelete = "DROP TABLE "+newTable;
            statement.executeUpdate(sqlDelete);
            
            String sqlCreate = "CREATE TABLE "+newTable+" (ROUND int,"
                    +"POINTS int)";
            statement.executeUpdate(sqlCreate);
            
            
            String sqlInsert = "INSERT INTO "+newTable+" VALUES("
                    +Integer.toString(Game.ROUND)
                    +","
                    +Integer.toString(handler.getWorld().getEntityManager().getPlayer().getPoints())
                    +")";
            
            statement.executeUpdate(sqlInsert);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
