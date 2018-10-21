package entities.statics;

import deadManWalking.Handler;
import deadManWalking.items.Item;
import gfx.Assets;
import java.awt.Color;
import java.awt.Graphics;
import deadManWalking.tiles.Tile;

/**
 *
 * @author matthewluthi
 */
public class Tree extends StaticEntity {
    public Tree(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2);
        
        bounds.x = 10;
        bounds.y = (int) (height / 1.5f);
        bounds.width = width - 20;
        bounds.height = (int) (height - height / 1.5f);
    }

    @Override
    public void tick() {
        
    }
    
    @Override
    public void die() {
        //Instead of normal x y pos of tree we can make item drop spawn in random location around border of
        //the rock or even create a random number of items to drop
        handler.getWorld().getItemManager().addItem(Item.woodItem.createNew((int) x, (int) y));
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), 
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        
        //Draw COLLISION BOX
        //g.setColor(Color.YELLOW);
        //Subtract game camera's offset to get proper position but also x boundary
        //co-ordinate is the offset(pixels) from current x position so must add it
        /*
        g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
               (int) (y + bounds.y - handler.getGameCamera().getyOffset()),
                bounds.width, bounds.height);
        */
    }
}
