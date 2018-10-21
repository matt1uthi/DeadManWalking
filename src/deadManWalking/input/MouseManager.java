package deadManWalking.input;

import deadManWalking.Handler;
import deadManWalking.ui.UIManager;
import entities.projectiles.Bullet;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *Getting mouse input for making menus and maybe shoot things in games or pickup things
 * @author matthewluthi
 */
public class MouseManager implements MouseListener, MouseMotionListener {
    private boolean leftPressed, rightPressed;
    private int mouseX, mouseY; //Hold position of mouse on screen
    private UIManager uiManager;//hold uiManager in mouseManager to relay mouse clicks here
    //private Handler handler;//NEW ADDED
    
    public MouseManager() {
        //this.handler = handler;
    }
    
    public void setUIManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }
    
    public boolean isLeftPressed() {
        return leftPressed;
    }
    
    public boolean isRightPressed() {
        return rightPressed;
    }
    
    public int getMouseX() {
        return mouseX;
    }
    
    public int getMouseY() {
        return mouseY;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Figure out what button is pressed
        if (e.getButton() == MouseEvent.BUTTON1) { //button1 is left mouse button
            leftPressed = true; //if left button is being fired, set true
            
            //NEW CODE
            /*mouseX = (int) (e.getX() + handler.getGameCamera().getxOffset());
            mouseY = (int) (e.getY() + handler.getGameCamera().getyOffset());
            
            
            handler.getWorld().getEntityManager().addEntity(new Bullet(handler, 
                    handler.getWorld().getEntityManager().getPlayer().getX(), handler.getWorld().getEntityManager().getPlayer().getY(), 8, 8, mouseX, mouseY));
            
            */
            
        } else if (e.getButton() == MouseEvent.BUTTON3) {//Button3 is right mouse button
            //button2 is actually middle mouse button implement if you want to
            rightPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Once one of these buttons is release we must set to false
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = false;
        }
        
        if (uiManager != null) {
            uiManager.onMouseRelease(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //Get position of mouse at any given time
        //This event is fired every time we move the mouse
        mouseX = e.getX();
        mouseY = e.getY();
        
        //Check if null so we don't force MouseManager to have uiManager instance
        if (uiManager != null) {
            uiManager.onMouseMove(e);
        }
    }
    
}
