package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import deadManWalking.Game;
import deadManWalking.Handler;

/**
 *Every entity needs a position on the screen (x, y), 
 * tick method and
 * render method, 
 * if making giant game and need efficient collision detection loop up
 * quadtrees
 * @author matthewluthi
 */
public abstract class Entity {
    //Calculations in game are not perfect integers,
    //so we use float to create smooth feel of pixels in game
    protected float x, y; //Position of entity on screen
    protected int width, height; //Size of entity
    //To access the keyManager and all input stuff
    protected Handler handler;
    //Collision area/bounds of entity
    protected Rectangle bounds;
    
    //HEALTH used to be in creature class but now
    //all entities have health variables
    protected int health;
    public static final int DEFAULT_HEALTH = 1; //USED TO BE 10
    
    //Whenever we set active to false, we will add code
    //to entityManager to remove that entity
    //and whenever we create entity it is set to active
    protected boolean active = true;
    
    //Starting position of our entity, need to initialize 
    //so we can do rendering
    public Entity(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        health = DEFAULT_HEALTH;
        
        //By default the bounding box (exactly the same size as the entity)
        //will be upper left corner and width and height of entity
        bounds = new Rectangle(0, 0, width, height);
    }
    
    //update variables and move entity
    public abstract void tick();
    
    //Draw to the screen
    public abstract void render(Graphics g);
    
    //Every entity has to own its own die method
    public abstract void die();
    
    /**
     * decrease an entities health by amount passed in
     * then check if health less than 0(remove from game)
     * call die() if health is less than 0
     * @param amt take in some amount
     */
    public void hurt(int amt) {
        health -= amt;
        if (health <= 0) {
            active = false; //Remove from the game
            die();
        }
    }
    
    /**
     * return new rectangle with x position of our entity + offset of bounding box of entity
     * same as y. IT COVERS THE AREA OF THE ENTITY THAT IS solid ie collision box
     * @param xOffset
     * @param yOffset
     * @return the bounding rectangle (area around this entity)
     */
    public Rectangle getCollisionBounds(float xOffset, float yOffset) {
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), 
        bounds.width, bounds.height);
    }
    
    /**
     * Test every entity in the game and test if it collides with THIS entity
     * @param xOffset
     * @param yOffset
     * @return 
     */
    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        //Use simple loop by getting entites and looping through every entity
        for (Entity e: handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this)) {
                //We are looping through every single entity in the game including
                //itself so we have to skip collision checking with itself...
                //Skip rest of code in loop and check next entity
                continue;
            }
            //If entity we are looping through intersects our collisionbounds in this method
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        
        return false;//no collisions
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getHealth() {
        return health;
    }

    public boolean isActive() {
        return active;
    }
    
    

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
