package gfx;

import com.sun.javafx.iio.ImageLoader;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *Load images for us
 * @author matthewluthi
 */
public class LoadImage {
    //Java stores images in a BufferedImage object
    //Method to load our images, static to access from anywhere
    public static BufferedImage loadImage(String path) {
        try {
            //Load and return image to method
            return ImageIO.read(LoadImage.class.getResource(path));
        } catch (IOException e) {
            //If we don't load image into the game
            //we don't want to run the game
            System.exit(1);
        }
        return null;
    }
}
