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
public class Rock extends StaticEntity {

    public Rock(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);

        bounds.x = 3;
        bounds.y = (int) (height / 2f);
        bounds.width = width - 6;
        bounds.height = (int) (height - (height / 2f));
    }

    @Override
    public void tick() {

    }

    /**
     * Whenever we will kill or mine a rock a new pickable item will appear
     */
    @Override
    public void die() {
        //Instead of normal x y pos of rock we can make item drop spawn in random location around border of
        //the rock or even create a random number of items to drop
        handler.getWorld().getItemManager().addItem(Item.rockItem.createNew((int) x, (int) y));
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.rock, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        //g.setColor(Color.blue);

        /*
        g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
                (int) (y + bounds.y - handler.getGameCamera().getyOffset()),
                bounds.width, bounds.height);
        */
    }
}
