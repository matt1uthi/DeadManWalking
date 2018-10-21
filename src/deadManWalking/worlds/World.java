package deadManWalking.worlds;

import java.awt.Graphics;
import deadManWalking.Handler;
import deadManWalking.items.ItemManager;
import entities.EntityManager;
import entities.creatures.Player;
import entities.creatures.Zombie;
import entities.projectiles.Bullet;
import entities.statics.Headstone;
import entities.statics.Rock;
import entities.statics.Tree;
import deadManWalking.tiles.Tile;
import deadManWalking.utils.Utils;

/**
 *Draw every single tile in the game world which is our map
 * @author matthewluthi
 */
public class World {
    private Handler handler;
    //Width and height of world
    private int width, height; //5x5 is 5 tiles wide and high
    private int spawnX, spawnY;//spawn location for player
    private int[][] worldTiles;//our tile data  [x][y] positions
    
    //Entities
    private EntityManager entityManager;
    
    //Item
    private ItemManager itemManager;
    
    //Load a world from a file
    public World(Handler handler, String path) {
        this.handler = handler;
        entityManager = new EntityManager(handler, new Player(handler, 100,100));
        
        itemManager = new ItemManager(handler);
        
        entityManager.addEntity(new Tree(handler, 100, 250));
        entityManager.addEntity(new Tree(handler, 150, 350));
        entityManager.addEntity(new Rock(handler, 850, 150));
        entityManager.addEntity(new Tree(handler, 130, 420));
        entityManager.addEntity(new Rock(handler, 700, 100));
        entityManager.addEntity(new Headstone(handler, 70, 1100));
        entityManager.addEntity(new Headstone(handler, 350, 550));
        entityManager.addEntity(new Zombie(handler, 500, 200)); //80
        
        //entityManager.addEntity(new Zombie(handler, 520, 500));
        //entityManager.addEntity(new Zombie(handler, 200, 800));
        
        //entityManager.addEntity(new Zombie(handler, 800, 200));
        //entityManager.addEntity(new Zombie(handler, 550, 230));
        
        loadWorld(path);
        
        //Spawn positions for player in world file will now work
        entityManager.getPlayer().setX(spawnX);
        entityManager.getPlayer().setY(spawnY);
    }
    
    public void tick() {
        itemManager.tick();
        entityManager.tick();
    }
    
    //Draw all the tiles to the screen to seen the world
    public void render(Graphics g) {
        //Contains the tile the user can currently see on far left
        //This will set xStart using Math.max which will set it to either 0 (1st tile) or 
        //whatever the other calculation in 2nd argument is (left most seeable tile)
        //For tiles in the far left we are checking, is 0 (furthest left tile in whole world) greater than
        //how much the camera has moved in pixels / tile width(to get in terms of tiles), if gameCameara calculation
        //is greater than the 0 then we will use it because it means the player has moved right.
        //However if the player moves too far left, will will just use 0 so no errors from Math.max calculation
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
        //is the width (farthest right tile, smaller than gameCamera calculation where we add world width to xoffset
        //because we are now looking to the far right of the screen, similar to xStart
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
        int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
        int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);
        
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                //TILEWIDTH AND HEIGHT so we can larger amount of pixels
                //and space them out correctly
                getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()),
                        (int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));//Just subtracting
                //a variable yOffset from the position of where to render the tile at!
            }
        }
        
        //We want to render items below entities so we will render item first
        itemManager.render(g);
        
        //After rendering tiles we render entities
        entityManager.render(g);
    }
    
    public Tile getTile(int x, int y) {
        //Prevent error, check that x and y are inside  boundary of the map
        //so player cannot get outside the map
        if (x < 0 || y < 0 || x >= width || y>= height) {
            //Just make player stand on grass tile if it is less or more than boundary
            return Tile.grassTile;
        }
        Tile t = Tile.tiles[worldTiles[x][y]];
        if (t == null) {
            //return default tile
            return Tile.dirtTile;
        }
        return t;
    }
    
    //Get the file and load the data
    public void loadWorld(String path) {
        String file = Utils.loadFileAsString(path);
        //We must split on any amount of whitespace or newline character
        //which is "\\s+" and store in String array to access seperately
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);
        
        worldTiles = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //current index, getting 2d array to match 1d token array
                // we must add 4 because the first 4 elements in tokens[] are taken
                worldTiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
            }
        }
        
        /*width = 5;
        height = 5;
        tiles = new int[width][height];
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = 1;
            }
        }*/
    }

    public Handler getHandler() {
        return handler;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }
    
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    } 
}
