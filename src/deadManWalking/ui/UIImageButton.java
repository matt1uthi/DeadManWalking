package deadManWalking.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *image button because we might have another button that changes text like in
 * a shop that changes prices but not worried about text rendering at the moment
 * @author matthewluthi
 */
public class UIImageButton extends UIObject {
    
    private BufferedImage[] images;
    private ClickListener clicker;

    public UIImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
        super(x, y, width, height);
        //index 0 will be regular button but index 1 will be when user hovering over button
        this.images = images;
        this.clicker = clicker;
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
        if (hovering) {
            g.drawImage(images[1], (int) x, (int) y, width, height, null);
        } else {
            g.drawImage(images[0], (int) x, (int) y, width, height, null);
        }
    }

    @Override
    public void onClick() {
        //This saves us from making us classes for start button, load button etc
        //this way we create multiple ui image buttons and pass in a diff click listener
        //so we can perform different actions
        clicker.onClick();
    }
    
}
