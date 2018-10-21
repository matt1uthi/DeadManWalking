package gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 *We have to actually draw text to the screen requires some code
 * @author matthewluthi
 */
public class Text {
    /**
     * Method to draw text to the screen
     * We can either draw text uncentered at a specific point where 
     * bottom left corner of the text is at the xPos, yPos or
     * if we pass in true to be centered the text will be centered around
     * the point we pass in
     * @param g the paintbrush
     * @param text to display to screen
     * @param xPos x position of the text
     * @param yPos y position of the text
     * @param center do we want to have the text centered
     * @param color color of the text
     * @param font font of the text
     */
    public static void drawString(Graphics g, String text, int xPos, int yPos, 
            boolean center, Color color, Font font) {
        g.setColor(color);
        g.setFont(font);
        int x = xPos;
        int y = yPos;
        
        //If we want the text to be centered we have to change the
        //position of where xPos and yPos should be
        //NOTE: by default xPos and yPos refer to the bottom left corner of the textbox
        if (center) {
            //FontMetrics provides data about the font like width of characters
            //the height of all the characters etc, we use this to center
            //the text around a point
            FontMetrics fm = g.getFontMetrics(font);
            
            //xPos - pixel width of string we want to draw divided by 2
            x = xPos - fm.stringWidth(text) / 2;
            
            //getAscent is a correction value(amount of pixels that the font should
            //be drawn above the baseline, or above the line you want to draw the text at
            y = (yPos - fm.getHeight() / 2) + fm.getAscent();
        }
        
        g.drawString(text, x, y);
    }
}
