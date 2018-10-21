package states;

import gfx.Assets;
import java.awt.Graphics;
import deadManWalking.Game;
import deadManWalking.Handler;

/**
 *
 * @author matthewluthi
 */
public class SettingState extends State {
    public SettingState(Handler handler) {
        super(handler);
    }
    
    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.tree, 0, 0, null);
    }
    
}
