package entities.statics;

import deadManWalking.Handler;
import gfx.Assets;
import java.awt.Graphics;
import deadManWalking.tiles.Tile;

/**
 *
 * @author matthewluthi
 */
public class Headstone extends StaticEntity {

    public Headstone(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);
        bounds.x = 15;
        bounds.y = 20;
        bounds.width = width - 25;
        bounds.height = (int) (height - (height / 2f));
    }

    @Override
    public void tick() {
 
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.headStone, (int) (x - handler.getGameCamera().getxOffset()), 
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        
        /*
        g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()), 
                (int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
        */
    }

    @Override
    public void die() {
        
    }
    
}
