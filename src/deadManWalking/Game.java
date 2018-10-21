package deadManWalking;

import display.Display;
import entities.Entity;
import entities.creatures.Creature;
import entities.creatures.Player;
import entities.creatures.Zombie;
import gfx.Assets;
import gfx.GameCamera;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;
import deadManWalking.input.KeyManager;
import deadManWalking.input.MouseManager;
import states.GameState;
import states.GameOverState;
import states.HelpState;
import states.MenuState;
import states.SettingState;
import states.State;

/**
 * TILEGAME the main class of our game, holds all base code, start everything,
 * run everything and close everything, lots of stuff here
 *
 * The main framework of our game is init(), tick(), render() & GAME LOOP
 *
 * @author matthewluthi
 */
public class Game implements Runnable {//Allow class to run on its own thread

    public static int ROUND = 1;
    public static int MAX_ROUND = 50;

    private Display display;
    private int width, height;
    public String title;

    //Need variable to control if while loop runs or not
    private boolean running = false;
    //We need a thread to run on
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    //States
    public State gameState; //was private before:
    public State menuState;
    //public State gameOverState;
    public State helpState;

    //Input
    private KeyManager keyManager;
    private MouseManager mouseManager;

    //Camera
    private GameCamera gameCamera;

    //Handler
    private Handler handler;

    //private BufferedImage testImage;
    //private SpriteSheet sheet;
    //Constructor whenever we create a Game instance it will
    //automatically create a display for it to have
    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }

    //Only runs once, initilizing graphics of our game
    private void init() {
        //Make the display run from the Game class
        //We set the new Display object here so it runs on the correct
        //thread
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);//Access keyboard

        //add mouselisteners to both jframe and canvas objects so that way
        //which ever one is active(focused) will actually fire mouse events
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        //our keyManager class impplements keyListener
        //Initilize bufferedImage in here, we already associated path with
        //the project build pack so jut us "/textures....etc
        //testImage = LoadImage.loadImage("/textures/sheet.png");
        //Create new SpriteSheet object
        //sheet = new SpriteSheet(testImage);
        //Load our spriteSheet and its subImages  
        Assets.init();

        handler = new Handler(this); //Takes game object

        //Initilize game camera as 0, 0 drawn as normal position
        //so nothing is shifted from original position
        gameCamera = new GameCamera(handler, 0, 0);

        //Initilize local states to new objects
        gameState = new GameState(handler);//Pass this Game object to constructor
        helpState = new HelpState(handler);
        //gameOverState = new GameOverState(handler);
        //settingState = new SettingState(handler);
        menuState = new MenuState(handler);

        //Make the current state we want to tick and render the gameState
        //we just created above
        State.setState(menuState); //gameState
    }

    //int x = 0;
    //Can also call it update() method
    //update everything for our game
    private void tick() {
        keyManager.tick();
        //x += 1.5;
        //If we actually have a state
        if (State.getState() != null) {
            //Because we initialize states in this.init() once
            //the uiManager of the handler object gets set to null
            //so we need a way of telling if we want to switch states
            //then we must create a new instance of the state object to
            //pass an updated version of handler(ie, game.uiManager) to tick
            //in the new state
            if (State.getState().isSwitchState()) {
                if (State.getState().equals(helpState)) {
                    State.setState(new HelpState(handler));
                }

                if (State.getState().equals(menuState)) {
                    State.setState(new MenuState(handler));
                }

                if (State.getState().equals(gameState)) {
                    // TODO: BECAUSE THE UPPER IF BRANCH WILL ALWAYS EXECUTE TRUE
                    //IT WILL LOADENEMYDB() MANY TIMES
                    //NEED TO FIX THIS FOR BETTER LOGIC
                    State.getState().setSwitchState(false);
                    //State.setState(new GameState(handler));
                    loadEnemyDB();
                }
            }
            //call tick method of that state
            State.getState().tick();
        }

        boolean enemiesExist = false;
        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e instanceof Player) {
                continue;
            }

            if (e instanceof Creature) {
                enemiesExist = true;
            }
        }

        if (!enemiesExist) {
            newRound();
        }
    }

    //Render everything for our game
    private void render() {
        //BufferStrategy tells the computer how it should draw to the screen
        /*
        Buffer is a 'hidden' computer screen within your computer
        ->not actually a screen but memory in computer that holds same data
        as actual computer screen
        1. We draw buffer, then end drawing
        2. Buffer moves to next buffer
        3. We still cannot see anything
        4.Buffer moves to actual screen then we can see it
        BUFFER->BUFFER->Actual screen
        This prevents flickering like in older video games since they
        draw to actual screens instead of hidden screens beforehand
         */
        bs = display.getCanvas().getBufferStrategy();

        //If first time running game, canvas has no bufferStrategy(meaning null)
        //Use 3 buffers maximum
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;//Exit method to avoid errors running below
        }

        //Graphics object is g since we use it a lot
        //Its like a magic paintbrush, draw full images, lines, boxes, 
        //shapes to the screen
        g = bs.getDrawGraphics(); //Create paintbrush

        //Clear screen
        //by clearing rectangle of graphics object
        //it clears certain portion of object but we want the whole
        //screen so we give arguments of upper left point then
        //the size of our screen NOTE: clears screen everytime we call render
        g.clearRect(0, 0, width, height);

        //Draw here!
        //TEST CODE:Fill rectangle and fill onto the screen
        //g.fillRect(0, 0, width, height);
        //g.drawRect(10, 50, 50, 70);
        //Tell java that everything I draw after this line of code
        //should be in the colour red
        //g.setColor(Color.red);
        //g.fillRect(0, 0, 50, 70);
        //Reset colour to diff colour
        //g.setColor(Color.green);
        //g.fillRect(0, 0, 10, 10);
        //Actually render the image that we loaded
        //g.drawImage(testImage, 0, 0, null);
        //Render part of our spriteSheet
        //Just the green player(at x:0,y:0 of SpriteSheet and height and width:32
        //at position 5,5 on our window
        //g.drawImage(sheet.crop(32, 0, 32, 32), 5, 5, null);
        //Render grass tile to screen
        //g.drawImage(Assets.grass, x, 10, null);
        if (State.getState() != null) {
            State.getState().render(g);
        }

        //End drawing!
        //We have to tell java we are done drawing
        //and switch buffers to show to screen
        bs.show();
        //Our graphics object disposed of
        g.dispose();
    }

    //Single method of Runnable interface
    //When you start application it is a big program with single thread
    //but you can have more threads to run at same time for efficiency
    @Override
    public void run() {
        init();

        //Frames(ticks) per second, this is how many times we want the tick
        //and render methods to run per second
        int fps = 60;
        //Why 1b, because 1b nanoseconds in each second
        //Instead of measuring time in seconds we will measure time in nanoseconds
        //as its much more precise, so we are dividing essentially 1sec by fps
        //Which is how many times we want to render&tick per second
        //This allows us to get the maximum amount of time we are allowed to have
        //to run the tick and render methods to achieve 60fps goal(run 60times per second)
        double timePerTick = 1_000_000_000 / fps;
        double delta = 0;
        long now;
        //Returns current time of our computer in nanoseconds
        long lastTime = System.nanoTime();
        //Add a FPS counter as a visual representation of exactly how many
        //times the tick and render methods are being called every second
        long timer = 0;
        int ticks = 0;

        //GAME LOOP of every game ever
        //1. Update all variables, positions of objects etc
        //2. Render(DRAW) everything to the screen
        //3. REPEAT
        while (running) { //Over and over again
            //FPS in game loop??
            //Current time in nano seconds
            now = System.nanoTime();
            //Add to delta variable the (now-lastTime) is amount of time elapsed
            //since we last called this statement then divide by max amount of time
            //allowed to call tick & render methods...Seeing how much time has passed
            //and seeing if we need to call tick and rendor methods or not
            delta += (now - lastTime) / timePerTick;
            //Amount of time since we last call this statement
            timer += (now - lastTime);
            //Reset the lastTime to current time for use in the next iteration of the loop
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                //We ticked & rendered so increment ticks
                ticks++;
                //We ticked & rendered so decrement delta to 0
                delta--;
            }

            //Verify ticks and frames being called 60 times per second
            if (timer >= 1_000_000_000) {
                System.out.println("Ticks and Frames:" + ticks);
                //Reset ticks and timers
                ticks = 0;
                timer = 0;
            }
        }

        //Stop the thread in case it hasn't been stopped already
        stop();

    }

    /**
     * The round number determines the amount of zombies to spawn during that
     * round
     */
    public void newRound() {
        Game.ROUND++;
        //Replenish ammo
        handler.getWorld().getEntityManager().getPlayer().setAmmo(
                handler.getWorld().getEntityManager().getPlayer().getMaxAmmo());
        for (int i = 0; i < Game.ROUND; i++) {
            spawnEnemy();
        }
    }

    /**
     * WHEN SPAWNING NEED TO CHECK THAT Zombie does not spawn on same player x y
     * bounds or another zombie's x y bounds or on a solid TILE!!!!!!!!!!!!!!!
     */
    public void spawnEnemy() {
        //The map is roughly 1200 x 1200 px 
        //so we will spawn an enemy in a random location within these x y positions
        Random rand = new Random();

        int xPos = 0;
        int yPos = 0;
        Zombie zombie = null;

        //Keep changing x y pos until the zombie does not collide with anything else
        do {
            xPos = rand.nextInt(1180); //200 1180
            yPos = rand.nextInt(1180); //1180

            zombie = new Zombie(handler, (float) xPos, (float) yPos);
        } while (zombie.checkEntityCollisions(xPos, yPos));

        //addCreatureEntities is a special method for adding(saving) the multiple zombie objects that we create within
        //a single game tick
        handler.getWorld().getEntityManager().addCreatureEntities(zombie);
    }

    /**
     * This method will be called in the game.tick() method, triggered when
     * a user wants to load the last played game, when the user loads a game
     * the database will update this game objects round number to the last known round number.
     * Since there is already 1 zombie upon initializing world object, we spawn 1 less enemy than usual
     * should re-implement this into
     * more modular code
     */
    public void loadEnemyDB() {
        for (int i = 1; i < Game.ROUND; i++) {
            spawnEnemy();
        }
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public GameCamera getGameCamera() {
        return gameCamera;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Start up our thread synchronized is for low level threads to sync threads
     */
    public synchronized void start() {
        //Set running to true before new thread
        if (running) {
            //If game is running already and is true we do
            //not want to accidently call start() somewhere otherwise
            //multiple messy threads can be started, return if running already
            return;
        }
        running = true;

        //Initilize thread and thread constructor takes the class
        //you want to run it on, which is game class since it implements Runnable
        thread = new Thread(this);
        //Begin thread by calling run method which has all our game code
        thread.start();
    }

    //Stop our thread
    public synchronized void stop() {
        if (!running) {
            //Safety check in case, so we do not 
            //run the code below
            return;
        }

        running = false;

        try {
            //Stop thread safely
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
