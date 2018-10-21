package states;

import deadManWalking.Handler;
import deadManWalking.ui.ClickListener;
import deadManWalking.ui.UIImageButton;
import deadManWalking.ui.UIManager;
import gfx.Assets;
import gfx.LoadImage;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author matthewluthi
 */
public class GameOverState extends State {
    private BufferedImage gameOverImg;
    private UIManager uiManager;//SHOULD GO IN STATE CLASS
    
    public GameOverState(Handler handler) {
        super(handler);
        gameOverImg = LoadImage.loadImage("/textures/gameOverImg.png");
        
        uiManager.addObject(new UIImageButton(0, 0, 54, 54, Assets.btn_back, new ClickListener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);
                
                handler.getGame().menuState.setSwitchState(true);
                State.setState(handler.getGame().menuState);
            } 
        }));
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(gameOverImg, 0, 0, null);
        uiManager.render(g);
    }
    
    
}
