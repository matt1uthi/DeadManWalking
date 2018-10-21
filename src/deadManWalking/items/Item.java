package deadManWalking.items;

import deadManWalking.Handler;
import gfx.Assets;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author matthewluthi
 */
public class Item {
    // Handler
    public static Item[] items = new Item[256]; //Array of items, storing 1 instance of every single item in game
    //like wood and rock item that has different id
    
    //id is important because when we get to making save files, we need to know where the item in game is
    //and what type the item is as well as amount of items the user has by using the id
    //so what we can do is access this items array at index 0 and it will return a wood item
    //and simply call create newItem method and create copy by passing in same data as these static items 
    //and return that item with x and y position

    public static Item woodItem = new Item(Assets.wood, "Wood", 0); 
    public static Item rockItem = new Item(Assets.smallRock, "Rock", 1);
    public static Item remainsItem = new Item(Assets.remains, "Remains", 2);
    
    //Class
    
    //Size that all items will be rendered at in game
    //if count every becomes -1 or Picked_up value then we have to remove item
    //from the world and into the player's inventory
    public static final int ITEMWIDTH = 32, ITEMHEIGHT = 32;
    
    protected Handler handler;
    protected BufferedImage texture;
    protected String name;
    protected final int id;
    
    protected Rectangle bounds;
    
    //count indicates the amount of items, instead of creating 50 items of the same object
    //for the player's inventory
    protected int x, y, count;
    protected boolean pickedUp = false;
    
    public Item(BufferedImage texture, String name, int id) {
        this.texture = texture;
        this.name = name;
        this.id = id;
        count = 1; 
        
        //can plug in x and y even though values haven't been
        //set in constructor yet
        bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);
        
        //Just like tile class
        //where id we passed in is items index
        items[id] = this;
    }
    
    /**
     * If the player's bounding rectangle overlaps the current item's
     * bounding rectangle, then it should be picked up, the player
     * walked over the item etc
     */
    public void tick() {
        if (handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
            pickedUp = true;
            //add items to inventory
            handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
        }
    }
    
    /*
    calls the render method below
    */
    public void render(Graphics g) {
        //We don't initialize handler in Item constructor so it could be null
        //so it might be null and can't access anything
        if (handler == null) {
            return;
        }
        render(g, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
    }
    
    /**
     * An item can be in two states, it can be in the game world lying on the ground or it can
     * be in the player's inventory, if it is in the inventory then we want to render it in a specific
     * place on screen(inventory slot) and in that case we use this render method with 3 parameters of x and y on it
     * so we can specify where on the screen to display that item to the user
     * but items can also be lying in the game world which is why they need x and y position so we use above
     * render method without x and y parameters
     * @param g graphics object to help us draw BufferedImage to screen
     * @param x position to display on inventory screen
     * @param y position to display on inventory screen
     */
    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
    }
    
    /**
     * Creates a copy of the item class currently here for a new item
     * @param x
     * @param y
     * @return 
     */
    public Item createNew(int x, int y) {
        //same texture, name and id
        Item i = new Item(texture, name, id);
        //convenient passed in parameters from this method
        i.setPosition(x, y);
        return i;
    }
    
    //Test method to generate an item instance and
    //will not add it to the game world but activate
    //variables to add to inventory
    public Item createNew(int count) {
        Item i = new Item(texture, name, id);
        i.setPickedUp(true);
        i.setCount(count);
        return i;
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        //Have to update boundary rectangle to make it work 
        //inside setPosition
        bounds.x = x;
        bounds.y = y;
    }
    
    //Getters & setters

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCount(int count) {
        this.count = count;
    }

    
    
    public Handler getHandler() {
        return handler;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCount() {
        return count;
    }
    
    
}
