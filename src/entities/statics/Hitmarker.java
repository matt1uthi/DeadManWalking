package entities.statics;

import deadManWalking.Handler;
import gfx.Assets;
import java.awt.Graphics;

/**
 *When a bullet hits an entity a hit marker should be displayed
 * @author matthewluthi
 */
public class Hitmarker extends StaticEntity {

    public Hitmarker(Handler handler, float x, float y) {
        super(handler, x, y, 32, 32);
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.hitmarker, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), null);
    }

    @Override
    public void die() {
        
    }
    
}
