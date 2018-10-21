package deadManWalking.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *Tile images, like grass, stone, dirt..base class for
 * every tile
 * @author matthewluthi
 */
public class Tile {
    //STATIC STUFF HERE, access from anywhere
    public static Tile[] tiles = new Tile[256];
    //id 0 will always mean grass
    //Static instances of a tile
    public static Tile grassTile = new GrassTile(0);
    public static Tile dirtTile = new DirtTile(1);
    public static Tile rockTile = new RockTile(2);
    public static Tile whiteTile = new WhiteTile(3);
    public static Tile blackTile = new BlackTile(4);
    
    //CLASS
    public static final int TILEWIDTH = 64, TILEHEIGHT = 64; 
    
    protected BufferedImage texture;
    //Identify tile, final because it should always be unique
    //never change it
    protected final int id;
    
    //Texture(image) of tile
    public Tile(BufferedImage texture, int id) {
       this.texture = texture; 
       this.id = id;
       
       //Tile array at element id as this tile being constructed
       tiles[id] = this;
    }
    
    //Anything else in game has to update variables, and draw to screen
    public void tick() {
        
    }
    //x and y as position on screen
    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
    }
    
    //We should be able to tell if we can walk on tile
    public boolean isSolid() {
        return false;
    }
    
    public int getId() {
        return id;
    }
}
