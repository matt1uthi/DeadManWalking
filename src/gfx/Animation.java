package gfx;

import java.awt.image.BufferedImage;

/**
 *
 * @author matthewluthi
 */
public class Animation {
    private int speed, index;
    private long lastTime, timer;
    private BufferedImage[] frames; //Number of frames to rotate through
    
    public Animation(int speed, BufferedImage[] frames) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }
    
    //Called every time we update game
    public void tick() {
        //Adding to timer amount of time passed since lance calling
        //tick method call and this tick method call
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        
        if (timer > speed) {
            index++;
            timer = 0;
            
            //So we don't get out of bounds exception
            if (index >= frames.length) {
                index = 0;
            }
        }
    }
    
    public BufferedImage getCurrentFrame() {
        return frames[index];
    }
}
