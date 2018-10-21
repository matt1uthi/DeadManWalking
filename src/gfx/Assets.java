package gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;
import states.State;
import states.MenuState;

/**
 * An asset is any image, sound or feature we can use in game to load the assets
 * once instead of rending and cropping multiple times in the GAMELOOP
 *
 * @author matthewluthi
 */
public class Assets {

    //Create variables to represent grid spaces on sprite sheet 32x32 usually
    //sprites are perfectly square or rectangular taking up 2 grid spaces
    //so we don't have to hard code all the numbers
    private static final int WIDTH = 32, HEIGHT = 32;

    //May want to load multiple versions of same font
    //but different sizes so this one is 28
    public static Font font28;

    //public static BufferedImage player;
    public static BufferedImage[] player_down, player_up, player_left, player_right;
    public static BufferedImage[] attack_up, attack_down, attack_left, attack_right;
    public static BufferedImage[] shoot_down, shoot_left, shoot_right;
    public static BufferedImage[] btn_start, btn_help, btn_options, btn_load, btn_back;
    public static BufferedImage[] zombie_down, zombie_up, zombie_left, zombie_right;

    public static BufferedImage dirt, grass, stone, rock, smallRock, tree, wood, 
            checkeredWhite, checkeredBlack, headStone, hitmarker, remains;

    public static BufferedImage inventoryScreen;

    public static BufferedImage[] grimReaper;
    public static BufferedImage[] knight;
    public static BufferedImage[] ninja;

    //init() method will only call once, so we load images/object once only
    public static void init() {
        font28 = FontLoader.loadFont("res/fonts/slkscr.ttf", 28);
        
        //Load in our sprite sheet
        SpriteSheet sheet = new SpriteSheet(LoadImage.loadImage("/textures/spriteSheet.png"));

        inventoryScreen = LoadImage.loadImage("/textures/inventoryScreen.png");

        //Animation is 2 frames long so make array 2 big
        //Array of images that make up walking down images of player
        player_down = new BufferedImage[2];
        player_up = new BufferedImage[2];
        player_left = new BufferedImage[2];
        player_right = new BufferedImage[2];
 
        grimReaper = new BufferedImage[2];
        knight = new BufferedImage[2];
        ninja = new BufferedImage[2];
        
        zombie_up = new BufferedImage[2];
        zombie_down = new BufferedImage[2];
        zombie_left = new BufferedImage[2];
        zombie_right = new BufferedImage[2];
        
        attack_up = new BufferedImage[2];
        attack_down = new BufferedImage[2];
        attack_left = new BufferedImage[2];
        attack_right = new BufferedImage[2];
        
        shoot_down = new BufferedImage[2];
        shoot_left = new BufferedImage[2];
        shoot_right = new BufferedImage[2];
        
        attack_up[0] = sheet.crop(WIDTH * 2, HEIGHT, WIDTH, HEIGHT);
        attack_up[1] = sheet.crop(WIDTH * 5, HEIGHT, WIDTH, HEIGHT);
        attack_down[0] = sheet.crop(0, HEIGHT, WIDTH, HEIGHT);
        attack_down[1] = sheet.crop(WIDTH * 4, HEIGHT, WIDTH, HEIGHT);
        attack_right[0] = sheet.crop(WIDTH, HEIGHT * 2, WIDTH, HEIGHT);
        attack_right[1] = sheet.crop(WIDTH * 4, HEIGHT * 2, WIDTH, HEIGHT);
        attack_left[0] = sheet.crop(WIDTH * 2, HEIGHT * 2, WIDTH, HEIGHT);
        attack_left[1] = sheet.crop(WIDTH * 5, HEIGHT * 2, WIDTH, HEIGHT);
        
        shoot_down[0] = sheet.crop(WIDTH * 6, 0, WIDTH, HEIGHT);
        shoot_down[1] = sheet.crop(WIDTH * 7, 0, WIDTH, HEIGHT);
        shoot_right[0] = sheet.crop(WIDTH * 6, HEIGHT, WIDTH, HEIGHT);
        shoot_right[1] = sheet.crop(WIDTH * 7, WIDTH, WIDTH, HEIGHT);
        shoot_left[0] = sheet.crop(WIDTH * 6, HEIGHT * 2, WIDTH, HEIGHT);
        shoot_left[1] = sheet.crop(WIDTH * 7, HEIGHT * 2, WIDTH, HEIGHT);
        
        btn_start = new BufferedImage[2];
        btn_start[0] = sheet.crop(WIDTH * 8, 0, WIDTH * 2, HEIGHT);
        btn_start[1] = sheet.crop(WIDTH * 8, HEIGHT, WIDTH * 2, HEIGHT);
        
        btn_help = new BufferedImage[2];
        btn_help[0] = sheet.crop(WIDTH * 8, HEIGHT * 2, WIDTH * 2, HEIGHT);
        btn_help[1] = sheet.crop(WIDTH * 8, HEIGHT * 3, WIDTH * 2, HEIGHT);
        
        btn_back = new BufferedImage[2]; 
        btn_back[0] = sheet.crop(WIDTH * 8, HEIGHT * 6, WIDTH, HEIGHT);
        btn_back[1] = sheet.crop(WIDTH * 9, HEIGHT * 6, WIDTH, HEIGHT);
        
        btn_options = new BufferedImage[2];
        btn_options[0] = sheet.crop(WIDTH * 8, HEIGHT * 4, WIDTH * 2, HEIGHT);
        btn_options[1] = sheet.crop(WIDTH * 8, HEIGHT * 5, WIDTH * 2, HEIGHT);
        
        btn_load = new BufferedImage[2];
        btn_load[0] = sheet.crop(WIDTH * 8, HEIGHT * 7, WIDTH * 2, HEIGHT);
        btn_load[1] = sheet.crop(WIDTH * 8, HEIGHT * 8, WIDTH * 2, HEIGHT);

        //Crop respective co-ordinates of sprite sheet and store in array
        player_down[0] = sheet.crop(0, HEIGHT, WIDTH, HEIGHT);
        player_down[1] = sheet.crop(WIDTH, HEIGHT, WIDTH, HEIGHT);
        player_up[0] = sheet.crop(WIDTH * 2, HEIGHT, WIDTH, HEIGHT);
        player_up[1] = sheet.crop(WIDTH * 3, HEIGHT, WIDTH, HEIGHT);
        player_right[0] = sheet.crop(0, HEIGHT * 2, WIDTH, HEIGHT);
        player_right[1] = sheet.crop(WIDTH, HEIGHT * 2, WIDTH, HEIGHT);
        player_left[0] = sheet.crop(WIDTH * 2, HEIGHT * 2, WIDTH, HEIGHT);
        player_left[1] = sheet.crop(WIDTH * 3, HEIGHT * 2, WIDTH, HEIGHT);
         
        zombie_down[0] = sheet.crop(0, HEIGHT * 5, WIDTH, HEIGHT);
        zombie_down[1] = sheet.crop(WIDTH, HEIGHT * 5, WIDTH, HEIGHT);
        zombie_up[0] = sheet.crop(WIDTH * 2, HEIGHT * 5, WIDTH, HEIGHT);
        zombie_up[1] = sheet.crop(WIDTH * 3, HEIGHT * 5, WIDTH, HEIGHT);
        zombie_right[0] = sheet.crop(0, HEIGHT * 6, WIDTH, HEIGHT);
        zombie_right[1] = sheet.crop(WIDTH, HEIGHT * 6, WIDTH, HEIGHT);
        zombie_left[0] = sheet.crop(WIDTH * 2, HEIGHT * 6, WIDTH, HEIGHT);
        zombie_left[1] = sheet.crop(WIDTH * 3, HEIGHT * 6, WIDTH, HEIGHT);
        
        remains = sheet.crop(WIDTH * 4, HEIGHT * 8, WIDTH, HEIGHT);
        checkeredWhite = sheet.crop(0, 0, WIDTH, HEIGHT);
        checkeredBlack = sheet.crop(WIDTH, 0, WIDTH, HEIGHT);
        //Set BufferedImage variables declared above to the appropriate areas
        //within the sprite sheet
        //player = sheet.crop(0, 0, WIDTH, HEIGHT);
        //Dirt tile is one grid(32 pixels) over from origin co-ordinate
        //so we specify x co-prdinate as width, y:0 
        dirt = sheet.crop(WIDTH * 4, 0, WIDTH, HEIGHT);
        //Grass tile is 3rd tile from origin so we specify width * 2
        //to get to the third tile
        grass = sheet.crop(WIDTH * 2, 0, WIDTH, HEIGHT);
        stone = sheet.crop(WIDTH * 3, 0, WIDTH, HEIGHT);
        //2nd row of images, y: is one grid down (32 pixels)
        tree = sheet.crop(WIDTH, HEIGHT * 8, WIDTH, HEIGHT * 2);
        wood = sheet.crop(WIDTH * 3, HEIGHT * 9, WIDTH, HEIGHT);
        rock = sheet.crop(WIDTH * 2, HEIGHT * 8, WIDTH, HEIGHT);
        smallRock = sheet.crop(WIDTH * 2, HEIGHT * 9, WIDTH, HEIGHT);
        headStone = sheet.crop(WIDTH * 3, HEIGHT * 8, WIDTH, HEIGHT);
        hitmarker = sheet.crop(WIDTH * 5, 0, WIDTH, HEIGHT);
        
        //Character selection
        grimReaper[0] = sheet.crop(WIDTH * 2, HEIGHT * 7, WIDTH, HEIGHT);
        grimReaper[1] = sheet.crop(WIDTH * 3, HEIGHT * 7, WIDTH, HEIGHT);

        knight[0] = sheet.crop(0, HEIGHT * 7, WIDTH, HEIGHT);
        knight[1] = sheet.crop(WIDTH, HEIGHT * 7, WIDTH, HEIGHT);
        
        ninja[0] = sheet.crop(WIDTH * 4, HEIGHT * 7, WIDTH, HEIGHT);
        ninja[1] = sheet.crop(WIDTH * 5, HEIGHT * 7, WIDTH, HEIGHT);
    }
    
    public static void initCharacter() {
        SpriteSheet sheet = new SpriteSheet(LoadImage.loadImage("/textures/spriteSheet.png"));
        
        //BufferedImage Arrays holding character movement images
        //Render character skin depending on what user selected in menuState
        if (MenuState.getCharacter() == Character.GRIM_REAPER) {
            //Crop respective co-ordinates of sprite sheet and store in array
            player_down[0] = sheet.crop(0, HEIGHT, WIDTH, HEIGHT);
            player_down[1] = sheet.crop(WIDTH, HEIGHT, WIDTH, HEIGHT);
            player_up[0] = sheet.crop(WIDTH * 2, HEIGHT, WIDTH, HEIGHT);
            player_up[1] = sheet.crop(WIDTH * 3, HEIGHT, WIDTH, HEIGHT);
            player_right[0] = sheet.crop(0, HEIGHT * 2, WIDTH, HEIGHT);
            player_right[1] = sheet.crop(WIDTH, HEIGHT * 2, WIDTH, HEIGHT);
            player_left[0] = sheet.crop(WIDTH * 2, HEIGHT * 2, WIDTH, HEIGHT);
            player_left[1] = sheet.crop(WIDTH * 3, HEIGHT * 2, WIDTH, HEIGHT);
        } else if (MenuState.getCharacter() == Character.KNIGHT) {
            player_down[0] = sheet.crop(0, HEIGHT * 3, WIDTH, HEIGHT);
            player_down[1] = sheet.crop(WIDTH, HEIGHT * 3, WIDTH, HEIGHT);
            
            player_up[0] = sheet.crop(WIDTH * 2, HEIGHT * 3, WIDTH, HEIGHT);
            player_up[1] = sheet.crop(WIDTH * 3, HEIGHT * 3, WIDTH, HEIGHT);
            
            player_right[0] = sheet.crop(0, HEIGHT * 4, WIDTH, HEIGHT);
            player_right[1] = sheet.crop(WIDTH, HEIGHT * 4, WIDTH, HEIGHT);

            player_left[0] = sheet.crop(WIDTH * 2, HEIGHT * 4, WIDTH, HEIGHT);
            player_left[1] = sheet.crop(WIDTH * 3, HEIGHT * 4, WIDTH, HEIGHT); 
        }
    }

}
