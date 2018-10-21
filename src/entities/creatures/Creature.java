package entities.creatures;

import entities.Entity;
import deadManWalking.Game;
import deadManWalking.Handler;
import deadManWalking.tiles.Tile;

/**
 *Abstract because we need to specify the type of creature
 * we want in the game
 * @author matthewluthi
 */
public abstract class Creature extends Entity {
    //Start health for every creature
    //Constants for something to rely on
    //public static final int DEFAULT_HEALTH = 10;
    public static final float DEFAULT_SPEED = 3.0f;
    //Square Tile-based game so pixels will be 64x64 square creatures
    //to be drawn to the screen(can be any size 16x16)
    public static final int DEFAULT_CREATURE_WIDTH = 64,
                            DEFAULT_CREATURE_HEIGHT = 64;
    
    //protected int health;
    protected float speed; //Each entity has a speed
    protected float xMove, yMove;
    
    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        //health = DEFAULT_HEALTH;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    }

    //Move method to adjust x and y variables of creature
    public void move() {
        /*
        Whenever player moves left or right(x axis) call move method
        check entity collisions where we are going to be moving to and
        place bounding box there
        (current x position + offset of bounding box + this xMove offset)
        */
        if (!checkEntityCollisions(xMove, 0f)) {
            moveX();
        }
        if (!checkEntityCollisions(0f, yMove)) {
            //here we pass in 0 for xOffset because we already checked for that above
            moveY();
        }
        
    }
    
    //Collision checking
    public void moveX() {
        if (xMove > 0) {
            //if xMove if + then moving up on x axis
            //creture moving RIGHT
            //(x + xMove) is where we are trying to move to
            //then get offset which is bounds.x
            //then get to edge of bounding box (bounds.width)
            //gets us the position in pixels, but we want x co-ordinate
            // of the tile we are trying to move into so divide TILEWIDTH
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
            //tx is temp var
            
            //Pass in tile and upper right corner of our bounding box
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                x += xMove; //if not collision move creature
            } else { //There was a collision, so reset x position entirely
                //so that bounding box is directly aligned with tile we tried to move into
                //so no matter what there will be no small gap
                //we subtract 1pixel to allow the player to slide up and down against tile
                x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
            }
            
        } else if (xMove < 0) {
            //if xMove is negative that means lower on x axis
            //which creature is moving LEFT
            //bounds.x is to the left portion of image
            int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
            
            //Pass in tile and upper right corner of our bounding box
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                x += xMove; //if not collision move creature
            } else {
                x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
            }
        }
        //x += xMove;
    }
    
    public void moveY() {
        if (yMove < 0) { //Moving up
            //Divivde by Tile.TILEHEIGHT to get it in terms of tiles
            int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
            
            //x + bounds.x will get upper left hand corner of collision box
            if (!collisionWithTile((int) (x + bounds. x) / Tile.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH , ty)) {
                y += yMove;
            } else {
                y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
            }
        } else if (yMove > 0) { //Moving down
            //Divivde by Tile.TILEHEIGHT to get it in terms of tiles
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
            
            //x + bounds.x will get upper left hand corner of collision box
            //&& x + bounds.x + bounds.width to get upper right hand corner
            if (!collisionWithTile((int) (x + bounds. x) / Tile.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH , ty)) {
                y += yMove;
            } else {
                y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
            }
        }
    }
    
    //Make our lives easier to take some tile x & y co-ordinate
    //and return true if solid tile
    protected boolean collisionWithTile(int x, int y) {
        return handler.getWorld().getTile(x, y).isSolid();
    }
    
    //GETTERS SETTERS
    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }

    
    public float getxMove() {
        return xMove;
    }

    public float getyMove() {
        return yMove;
    }

    
    public int getHealth() {
        return health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
      
}
