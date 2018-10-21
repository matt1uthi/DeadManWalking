package display;

import gfx.LoadImage;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *Manages the display of our game, such as the window
 * @author matthewluthi
 */
public class Display {
    
    //Private as other classes shouldn't have to access this
    private JFrame frame; //Our window object
    
    //Canvas allows us to draw graphics to it
    //Player image, tile image -> draw to canvas then add to JFrame
    //Ex. A painter will paint on a canvas then display it in a frame to show
    //audience
    private Canvas canvas;
    
    //Title, width and height
    private String title;
    private int width, height; //In terms of pixels(the little dots)
    
    public Display(String title, int width, int height) {
        //Initialize variables
        this.title = title;
        this.width = width;
        this.height = height;
        
        createDisplay();
    }
    
    /**
     * Create own method so we don't clutter the Display constructor
     */
    private void createDisplay() {   
        frame = new JFrame(title);
        frame.setSize(width, height);
        //Terminate program when window closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //False to allow our window to stay stuck as set size
        frame.setResizable(false);
        //When window first pops up on screen it appears in center and pretty
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        //Set the size we want our canvas to be which is width & height of our game
        canvas.setPreferredSize(new Dimension(width, height));
        //We use these two methods to make sure canvas is always the same size
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        //JFrame is only thing that has focus (allowing application priority
        //rather than the canvas we are drawing on
        canvas.setFocusable(false);
        
        //Add canvase to frame
        frame.add(canvas);
        
        
        //Resize our window a little bit to see all of the canvas at all times
        frame.pack();
    }
    
    public Canvas getCanvas() {
        return canvas;
    }
    
    //Just to access JFrame outside display class
    public JFrame getFrame() {
        return frame;
    }
}
