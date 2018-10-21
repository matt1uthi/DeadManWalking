package states;

import deadManWalking.Game;
import gfx.Assets;
import gfx.Character;
import java.awt.Graphics;
import deadManWalking.Handler;
import deadManWalking.ui.ClickListener;
import deadManWalking.ui.UIImageButton;
import deadManWalking.ui.UIManager;
import entities.creatures.Creature;
import gfx.Animation;
import gfx.LoadImage;
import gfx.Text;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author matthewluthi
 */
public class MenuState extends State {

    private UIManager uiManager;
    private static Character character;
    private BufferedImage background;
    private BufferedImage title;
    //Zombie bufferedImage to imitate loading bar
    private Animation uiZombieAnim;
    private int uiZombieX;

    public MenuState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        //We have to set this uiManager in the mouseManager so we can get events like hover
        handler.getMouseManager().setUIManager(uiManager);

        //uiZombie loading bar
        uiZombieAnim = new Animation(400, Assets.zombie_right);
        uiZombieX = 0;

        title = LoadImage.loadImage("/textures/DeadManTitleimg.png");
        background = LoadImage.loadImage("/textures/deadManBg.png");

        //START button
        uiManager.addObject(new UIImageButton(250, 220, 128, 64, Assets.btn_start, new ClickListener() {
            //Having ClickListener allows us to create anonymous classes on the fly for each button we need
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);

                State.setState(handler.getGame().gameState);
            }
        }));

        //HELP button
        uiManager.addObject(new UIImageButton(400, 220, 128, 64, Assets.btn_help, new ClickListener() {
            //Having ClickListener allows us to create anonymous classes on the fly for each button we need
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);

                handler.getGame().helpState.setSwitchState(true);
                //if we don't set to null mouseManager.uiManager will still manage the menuButtons instead of help buttons
                State.setState(handler.getGame().helpState);
            }
        }));

        //LOAD BUTTON
        uiManager.addObject(new UIImageButton(100, 220, 128, 64, Assets.btn_load, new ClickListener() {
            @Override
            public void onClick() {
                String url = "jdbc:derby:DeadmanDB; create=true";
                String username = "matt";
                String password = "12345";
                //Allow connection to our database (like a bridge)
                Connection conn = null;
                //Execute query on our database
                Statement statement = null;
                //ResultSet represents the data that the statement has done
                ResultSet resultSet = null;
                String query = "Select * from Matt.Leaderboard";

                try {
                    //TODO: MAKE DBOPERATIONS OBJECT GETQUERY() METHOD SO WE CAN SEPEARTE ATTRIBUTES
                    //TODO: LOAD the number of enemies left from the database make variables
                    //Makes reference of conObj to database so we can connect to it
                    //localhost indicates its in the projects folder
                    conn = DriverManager.getConnection(url, username, password); //"jdbc:derby://localhost:1527/DeadmanDB", "matt", "12345"
                    statement = conn.createStatement();
          
                    resultSet = statement.executeQuery(query);

                    //statObj.executeUpdate("INSERT INTO Leaderboard"+" VALUES ("+Integer.toString(Game.ROUND)+", "+Integer.toString(points)+")"); //'Knight'
                    int round = 0;
                    int points = 0;
                    //Getting data from database
                    while (resultSet.next()) {
                        round = resultSet.getInt("ROUND");
                        points = resultSet.getInt("POINTS");
                        //String name = resObj.getString("NAME");               
                    }
                    Game.ROUND = round;
                    if (handler.getWorld().getEntityManager().getPlayer() != null) {
                         System.out.println(handler.getWorld().getEntityManager().getPlayer().getPoints());
                        handler.getWorld().getEntityManager().getPlayer().setPoints(points);
                        System.out.println(handler.getWorld().getEntityManager().getPlayer().getPoints());
                    }

                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                handler.getMouseManager().setUIManager(null);
                handler.getGame().gameState.setSwitchState(true);

                State.setState(handler.getGame().gameState);
            }

        }));

        //ADD ANOTHER IMAGE SAYING REAPER SELECTED?
        //Grim reaper character select
        uiManager.addObject(new UIImageButton(275, 130, 84, 84, Assets.grimReaper, new ClickListener() {
            @Override
            public void onClick() {
                character = Character.GRIM_REAPER;
                Assets.initCharacter();
            }
        }));

        //Knight character selection
        uiManager.addObject(new UIImageButton(160, 130, 84, 84, Assets.knight, new ClickListener() {
            @Override
            public void onClick() {
                character = Character.KNIGHT;
                Assets.initCharacter();
            }
        }));

        //Ninja selection
        uiManager.addObject(new UIImageButton(390, 130, 84, 84, Assets.ninja, new ClickListener() {
            @Override
            public void onClick() {
                character = Character.NINJA;
                Assets.initCharacter();
            }
        }));
    }

    @Override
    public void tick() {
        //Print mouse x y positions to console to see if working for testing
        //System.out.println(handler.getMouseManager().getMouseX()+
        //       "  "+handler.getMouseManager().getMouseY());
        //if (handler.getMouseManager().isLeftPressed() && handler.getMouseManager().isRightPressed()) {
        //    State.setState(handler.getGame().gameState);
        //}
        uiManager.tick();

        //Tick uiZombie which is just an animation
        uiZombieAnim.tick();

        //TEMPORARILY just go directly to the GameState, skip the menu state!
        //handler.getMouseManager().setUIManager(null);
        //State.setState(handler.getGame().gameState);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
        g.drawImage(title, 115, 0, null);

        //g.drawImage(background, 0, 0, handler.getWidth(), handler.getHeight(), null);
        uiManager.render(g);

        //if uiZombie has reached end of screen reset x position
        if (uiZombieX > handler.getGame().getWidth()) {
            uiZombieX = -10;
        }
        //render uiZombie to screen
        g.drawImage(uiZombieAnim.getCurrentFrame(), uiZombieX++, 295, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, null);

        Text.drawString(g, "Choose", 5, 160, false, Color.WHITE, Assets.font28);
        Text.drawString(g, "Player", 5, 180, false, Color.WHITE, Assets.font28);
        Text.drawString(g, "Knight", 135, 120, false, Color.YELLOW, Assets.font28);
        Text.drawString(g, "Reaper", 260, 120, false, Color.YELLOW, Assets.font28);
        Text.drawString(g, "Ninja", 390, 120, false, Color.YELLOW, Assets.font28);

        //TEST CODE
        //g.setColor(Color.red);
        //g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 100, 100);
    }

    public static Character getCharacter() {
        return character;
    }

}
