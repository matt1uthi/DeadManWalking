package deadManWalking.inventory;

import deadManWalking.Handler;
import deadManWalking.items.Item;
import gfx.Assets;
import gfx.Text;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Color;

/**
 *Rendering the list to the screen the invListCenterX & Y
 * is important because it is the middle point of the list
 * and each one of the list items should be spaced 30px apart
 * vertically
 * @author matthewluthi
 */
public class Inventory {
    private Handler handler;
    private boolean active = false;
    
    //Hard coded values to position inv menu on screen
    private int invX = 64, invY = 10, 
            invWidth = 512, invHeight = 340,
            invListCenterX = invX + 171,
            invListCenterY = invY + invHeight / 2 + 5;
            //CenterX & Y is the center of the inv list menu
    private int invListSpacing = 27; //space between each slot vertically
    
    //Co-ord of inventory image
    private int invImageX = 452, invImageY = 82,
            invImageWidth = 64, invImageHeight = 64;
    
    //co-ordinate of inventory count display
    private int invCountX = 484, invCountY = 172;
    
    //This is the current selected index of the inventoryItems array
    private int selectedItem = 0;
    
    private ArrayList<Item> inventoryItems;
    
    public Inventory(Handler handler) {
        this.handler = handler;
        inventoryItems = new ArrayList<Item>();
        
        //TESTING to add items to inv temporarily
        //addItem(Item.rockItem.createNew(5));
        //addItem(Item.woodItem.createNew(3));
    }
    
    public void tick() {
        //E key for inventory
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
            //Turning on and off inventory screen by setting inverse
            //ie. if active is true we will set to false or if active is false we set to true
            active = !active;
        }
        //The inventory screen is not gonna be on the screen
        //all the time so before we tick or render anything
        //we should check if inventory should be displayed
        if (!active) {
            return;
        }
        
        //Creating a way to get the list to move around by the keyboard
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            selectedItem--; //Move list down
        }
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            selectedItem++;  //Move list up
        }
        
        //
        if (selectedItem < 0) {
            //If less than 0 then we will set selectedItem
            //to the opposite side of the list, and loop around
            selectedItem = inventoryItems.size() - 1;//end of list
        } else if (selectedItem >= inventoryItems.size()) {
            //Start at the beginning of the list again
            selectedItem = 0;
        }
        
        //TEST code
        /*System.out.println("INVENTORY:");
        for (Item i : inventoryItems) {
            System.out.println(i.getName() + " "+ i.getCount());
        }*/
    }
    
    public void render(Graphics g) {
        if (!active) {
            return;
        }
        
        g.drawImage(Assets.inventoryScreen, invX, invY, invWidth, invHeight, null);
        
        //length of inventory array
        int len = inventoryItems.size();
        if (len == 0) {
            //If no items to render in inventory then return
            return;
        }
        
        //We have to display items if there are items
        //Because we base the math on selectedItem being the center point
        //we have 5 extra list slots above it, and 5 below it  to display items in
        //Start loop at -5 because we begin 5 items above selected item and go until
        //less than 6(which is the last slot 5 positions down)
        for (int i = -5; i < 6; i++) {
            //There might be some empty slots above or below the selected item
            //so that means it would be out of bounds  and there is no item to render at index
            //so skip it
            if (((selectedItem + i) < 0) || ((selectedItem + i) >= len)) {
                continue; //skip the code
            }
            
            //If we are centered on selectedItem, cosmetically enhance item
            if (i == 0) {
                Text.drawString(g, "> "+inventoryItems.get(selectedItem + i).getName()+" <", invListCenterX, 
                    (invListCenterY + (i * invListSpacing)), true, Color.ORANGE, Assets.font28);
            } else {
                Text.drawString(g, inventoryItems.get(selectedItem + i).getName(), invListCenterX, 
                    (invListCenterY + (i * invListSpacing)), true, Color.WHITE, Assets.font28);
            }
            
            //Display image and count of item
            //after loop so we just focus on the specific item
            Item item = inventoryItems.get(selectedItem);
            //Some values have to be tweaked to work with our screen size
            g.drawImage(item.getTexture(), invImageX, invImageY - 46, invImageWidth, invImageHeight, null);
            Text.drawString(g, Integer.toString(item.getCount()), invCountX, invCountY - 53, true, Color.WHITE, Assets.font28);
        }
        
        //render text
        //Text.drawString(g, "> Rock Item <", invListCenterX, invListCenterY, true, Color.WHITE, Assets.font28);
    }
    
    //2 inventory methods
    public void addItem(Item item) {
        //TEMPORARILY COMMENTED
        for (Item i : inventoryItems) {
            //If id of item in arrayList is the same thing as
            //the item we are trying to add to inventory
            if (i.getId() == item.getId()) {
                //then we set the count that is already in inventory
                //to count of that item + new item count we are trying to add
                i.setCount(i.getCount() + item.getCount());
                return;
            }
        }
        //If we already have an item, then it just adds to the item count above
        //otherwise we will actually add the item to inventory
        inventoryItems.add(item);
    }
    
    //Getters & setters
    public Handler getHandler() {
        return handler;
    }
    
    public void setHandler(Handler handler) {
        this.handler = handler;
    }     

    public boolean isActive() {
        return active;
    } 

}
