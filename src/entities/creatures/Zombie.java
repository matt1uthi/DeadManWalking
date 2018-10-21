package entities.creatures;

import deadManWalking.Handler;
import deadManWalking.items.Item;
import entities.statics.Headstone;
import gfx.Animation;
import gfx.Assets;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author matthewluthi
 */
public class Zombie extends Creature {
    //Zombie must wait 500 milliseconds before it can move again
    //MoveTimer initilized to cooldown value so when game starts it will move straight away
    private long lastMoveTimer, moveCooldown = 200, moveTimer = moveCooldown;
    
    //Animations
    private Animation animDown, animUp, animLeft, animRight;
    
    public Zombie(Handler handler, float x, float y) {
        super(handler, x, y, 64, 64);
        //Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT
        animDown = new Animation(400, Assets.zombie_down);
        animUp = new Animation(400, Assets.zombie_up);
        animLeft = new Animation(400, Assets.zombie_left);
        animRight = new Animation(400, Assets.zombie_right);
    }
    
    public void chooseRandomDirection() {
        //adding elapsed time between these two method calls
        //to moveTimer
        moveTimer += System.currentTimeMillis() - lastMoveTimer;
        
        //set up for next call of this system timer
        lastMoveTimer = System.currentTimeMillis();
        
        //if moveTimer has not been running 500ms or more
        //then zombie must wait to run the code, ie return to skip code
        if (moveTimer < moveCooldown) {
            return;
        }
        
        Random r = new Random();
        
        //Array holding possible return values
        int[] randomDirections = new int[3];
        randomDirections[0] = -4;
        randomDirections[1] = 0;
        randomDirections[2] = 4;
        
        //the random choice of which array index to return (0, 1, 2)
        int choice = r.nextInt(3);
        
        //Zombie will move so reset timer for next tick iteration
        moveTimer = 0;
        
        setxMove((float) randomDirections[choice]);
        setyMove((float) randomDirections[choice]);
    }

    @Override
    public void tick() {
        //Animations
        animDown.tick();
        animUp.tick();
        animLeft.tick();
        animRight.tick();
        
        move();
        chooseRandomDirection();
    }

    @Override
    public void render(Graphics g) {
        //g.fillRect((int) (x ), width, width, height);
        
        g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), 
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        
   
        /*
        g.setColor(Color.RED);
        if (this != null) {
            g.fillRect((int) (x - handler.getGameCamera().getxOffset()), 
                    (int) (y - handler.getGameCamera().getyOffset()), width, height);
        }
        */
    }
    
    
    //So we don't have to hardcode it in render
    private BufferedImage getCurrentAnimationFrame() {
        //Display certain image depending on where we are moving
        if (xMove < 0) { //Moving left because less than 0
            return animLeft.getCurrentFrame();
        } else if (xMove > 0) {
            return animRight.getCurrentFrame();
        } else if (yMove < 0) {
            return animUp.getCurrentFrame();
        } else if (yMove > 0) {
            return animDown.getCurrentFrame();
        } else {
            //Can default to standing animation
            return animDown.getCurrentFrame();
        }
    }

    @Override
    public void die() {
        handler.getWorld().getItemManager().addItem(Item.remainsItem.createNew((int) x, (int) y));
        //handler.getWorld().getEntityManager().addMobileEntity(new Headstone(handler, (int) x, (int) y));
    }
    
}
