package deadManWalking.tiles;

import gfx.Assets;

/**
 *
 * @author matthewluthi
 */
public class RockTile extends Tile {
    
    public RockTile(int id) {
        super(Assets.stone, id);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }
    
}
