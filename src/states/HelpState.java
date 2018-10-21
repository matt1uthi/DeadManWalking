package states;

import deadManWalking.Handler;
import deadManWalking.ui.ClickListener;
import deadManWalking.ui.UIImageButton;
import deadManWalking.ui.UIManager;
import gfx.Assets;
import gfx.LoadImage;
import gfx.Text;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author matthewluthi
 */
public class HelpState extends State {
    private UIManager uiManager;
    private BufferedImage helpMenu;
    
    public HelpState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);
        helpMenu = LoadImage.loadImage("/textures/HelpMenu.png");
        
        //0, 90
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
        //g.setColor(Color.BLACK);
        //g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
        
        g.drawImage(helpMenu, 0, 0, null);
        //Text.drawString(g, "Welcome to dead man walking", 0, 50, false, Color.yellow, Assets.font28);
        //Text.drawString(g, "You are the lone survivor", 0, 80, false, Color.yellow, Assets.font28);
        
        uiManager.render(g);
        
    }
    
}
