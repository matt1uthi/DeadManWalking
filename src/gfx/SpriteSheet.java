package gfx;

import java.awt.image.BufferedImage;

/**
 *CodeNMore spritesheet class to handle getting each picture withing the
 * main picture
 * @author matthewluthi
 */
public class SpriteSheet {
    //We have our entire spriteSheet image here but we need to crop it to
    //a single box of the sheet grid
    private BufferedImage sheet;
    
    public SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }
    
    //Crop method returning bufferedImage of just the pixel area we want ie
    //the first grid
    public BufferedImage crop(int x, int y, int width, int height) {
        //Crop spriteSheet image to the subimage we want
        return sheet.getSubimage(x, y, width, height);
    }
}
