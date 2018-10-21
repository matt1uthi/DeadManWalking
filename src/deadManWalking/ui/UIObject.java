package deadManWalking.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 * Core buttons, sliders anything the user will interact with menu wise in the
 * future
 *
 * @author matthewluthi
 */
public abstract class UIObject {

    protected float x, y;
    protected int width, height;
    protected boolean hovering = false;
    protected Rectangle bounds;

    public UIObject(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        bounds = new Rectangle((int) x, (int) y, width, height);
    }
    
    public abstract void tick();
    //Render everything
    public abstract void render(Graphics g);
    //Every single UIObject will recognize when a user clicks on it
    //Even though not every buttion etc might not need it
    public abstract void onClick();

    //Take in mouse events as a parameter when we move the mouse
    //we have to check if user's mouse is over the button(UIObject) so we can
    //change the image we render
    public void onMouseMove(MouseEvent e) {
        if (bounds.contains(e.getX(), e.getY())) {
            hovering = true;
        } else {
            hovering = false;
        }
    }
    
    //Detect if UIObject has been clicked or not(ie release)
    public void onMouseRelease(MouseEvent e) {
        //so if mouse is hovering over UIObject and is release boom we clicked it
        if (hovering && (e.getButton() == MouseEvent.BUTTON1)) {
            onClick();
        }
    }
    
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

    
    
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isHovering() {
        return hovering;
    }

    
}
