package deadManWalking.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyListener interface allows us to tell which keys are being pressed, Every
 * key on keyboard has an id attached to it
 *
 * Combat can be done many ways but we will use arrow keys as attack buttons, or
 * keep track of direction of player and attack in current direction etc
 *
 * @author matthewluthi
 */
public class KeyManager implements KeyListener {

    private boolean[] keys, justPressed, cantPress;
    //if true, move player up, down etc..
    //up arrow key being pressed then set up variable true
    public boolean up, down, left, right;
    //If you only have one button for attack then you only need one bool....
    public boolean aUp, aDown, aLeft, aRight; //attack buttons
    
    public boolean shoot;

    public KeyManager() {
        keys = new boolean[256];
        justPressed = new boolean[keys.length]; //make everything same array length
        cantPress = new boolean[keys.length];
    }

    //Tick method being called many times
    public void tick() {
        //Logic for opening inventory or menu screens!
        //loop through every single key code in array
        for (int i = 0; i < keys.length; i++) {
            //2. then we wait until the key is no longer pressed
            //meaning the key is released before we are able to
            //return true again in the justPressed array
            if (cantPress[i] && !keys[i]) {
                cantPress[i] = false;
            } else if (justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            
            //1.Make sure we are able to press the key
            //&& that key is being pressed
            if (!cantPress[i] && keys[i]) {
                //to set justPressed array to true
                justPressed[i] = true;
            }
        }

        //Test code
        /*if (keyJustPressed(KeyEvent.VK_SPACE)) {
            System.out.println("SPACE JUST PRESSED");
        }*/

        //Here we use WASD controls but can also
        //use arrowkeys like KeyEvent.VK_UP
        //up = (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]);
        //down = (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]);
        //left = (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]);
        //right = (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]);
        up = (keys[KeyEvent.VK_W]);
        down = (keys[KeyEvent.VK_S]);
        left = (keys[KeyEvent.VK_A]);
        right = (keys[KeyEvent.VK_D]);

        aUp = (keys[KeyEvent.VK_UP]);
        aDown = (keys[KeyEvent.VK_DOWN]);
        aLeft = (keys[KeyEvent.VK_LEFT]);
        aRight = (keys[KeyEvent.VK_RIGHT]);
        
        //spacebar to activate shoot var
        shoot = (keys[KeyEvent.VK_SPACE]);
    }

    public boolean keyJustPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= keys.length) {
            return false;
        }
        return justPressed[keyCode];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //Whenever user pressed a key
    @Override
    public void keyPressed(KeyEvent e) {
        //Make this less error prone to check bounds etc
        if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) {
            return;
        }
        //Get the keycode of event key, make it the index
        //and set to true
        keys[e.getKeyCode()] = true;
        //System.out.println("Pressed!");
    }

    //Whenever user stops pressing a key this method is called
    @Override
    public void keyReleased(KeyEvent e) {
        //false key not being pressed
        keys[e.getKeyCode()] = false;
    }

}
