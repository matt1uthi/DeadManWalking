package gfx;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

/**
 * Must be any other .ttf FONT 
 *Almost exactly like loadImage in loadImage
 * @author matthewluthi
 */
public class FontLoader {
    public static Font loadFont(String path, float size) {
        try {
            //Type of font is tt font and add formatting through deriveFont
            return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
                    
        } catch (FontFormatException | IOException e) {
           e.printStackTrace();
           //If fail then exit becuase our game will not be
           //able to run without a font
           System.exit(1);
        } 
        return null;
    }
}
