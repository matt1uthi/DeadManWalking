package deadManWalking.items;

import deadManWalking.Handler;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *Similar to entityManager but not as much code
 * this itemManager is not the inventory, just store the items
 * in the game currently lying on the ground
 * @author matthewluthi
 */
public class ItemManager {
    private Handler handler;
    private ArrayList<Item> items;
    
    //constructor to set handler object
    public ItemManager(Handler handler) {
        this.handler = handler;
        items = new ArrayList<Item>();
    }
    
    public void tick() {
        Iterator<Item> iterator = items.iterator();
        
        //While there is another item
        while (iterator.hasNext()) {
            Item i = iterator.next();
            i.tick(); //tick that item
            
            //If count is -1 then the player has picked it up
            //so we should remove it from the world
            if (i.isPickedUp()) {
                iterator.remove();
            }
        }
    }
    
    //Render all items, here we only have one parameter
    //because we want to use the item's x and y position in the world
    public void render(Graphics g) {
        for (Item i : items) {
            i.render(g);
        }
    }
    
    public void addItem(Item item) {
        //Whenever we add item to itemManager we want to set handler
        //so it is not null and it renders to the screen
        item.setHandler(handler);
        items.add(item);
    }

    public Handler getHandler() {
        return handler;
    }
    
    
}
