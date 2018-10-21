package deadManWalking.tiles;

import gfx.Assets;

/**
 *You should be able to walk on a grass tile and not a rock tile
 * grass doesn't override isSolid method so superclass will return false!
 * @author matthewluthi
 */
public class GrassTile extends Tile {
    
    public GrassTile(int id) {
        super(Assets.grass, id);
    }
    
}
