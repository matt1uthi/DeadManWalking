package deadManWalking;

import gfx.GameCamera;
import deadManWalking.input.KeyManager;
import deadManWalking.input.MouseManager;
import deadManWalking.worlds.World;

/**
 *This class allows us to just pass 1 object(Handler object) and
 * access our game object, world object and anything else we need
 * This is needed for collision detection which we need access to
 * the world and game which is found here
 * @author matthewluthi
 */
public class Handler {
    private Game game;
    private World world;
    
    public Handler(Game game) {
        this.game = game;
    }
    
    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }
    
    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }
    
    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }

    public int getWidth() {
        return game.getWidth();
    }
    
    public int getHeight() {
        return game.getHeight();
    }
    
    public Game getGame() {
        return game;
    }

    public World getWorld() {
        return world;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setWorld(World world) {
        this.world = world;
    }
    
    
}
