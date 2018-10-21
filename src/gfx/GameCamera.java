package gfx;

import entities.Entity;
import deadManWalking.Game;
import deadManWalking.Handler;
import deadManWalking.tiles.Tile;

/**
 *Create camera to move around the portion of the map we can 
 * see to move around the map
 * @author matthewluthi
 */
public class GameCamera {
    //Position, numbers that tell us how far off we draw
    //something from its original position (add or subtract from
    //orginal position in pixels
    //ex. a tile is 0, 0 and xoffset=10 then new tile position is -10, 0
    private float xOffset, yOffset;
    private Handler handler;
    
    public GameCamera(Handler handler, float xOffset, float yOffset) {
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    //Check if camera is showing blank space from outside
    //of the map and if it is fix it
    public void checkBlankSpace() {
        //From the x axis xOffset should be 0
        //to check left hand side of map
        if (xOffset < 0) {
            //Stop the camera scrolling
            xOffset = 0;
        } else if (xOffset > handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getWidth()) {
            //we get the map width * TILEWIDTH to get number of pixels
            xOffset = handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getWidth();
        }
        
        //check upper hand side of map
        if (yOffset < 0) {
            yOffset = 0;
        } else if (yOffset > handler.getWorld().getHeight() * Tile.TILEHEIGHT - handler.getHeight()) {
            yOffset = handler.getWorld().getHeight() * Tile.TILEHEIGHT - handler.getHeight();
        }
    }
    
    //Set xOffset, yOffset to correct numbers so
    //it centers on entity
    public void centerOnEntity(Entity e) {
        //x position of entity - screen width / 2 to center entity
        xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
        yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;
        
        //After calculations check blankspace
        checkBlankSpace();
    }

    //add x amount to xOffset etc..
    //Move method that adds to those variables
    public void move(float xAmt, float yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;
        
        checkBlankSpace();
    }
    
    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
    
    
}
