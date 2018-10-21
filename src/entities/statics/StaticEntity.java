package entities.statics;

import deadManWalking.Handler;
import entities.Entity;

/**
 * When something is static is something that doesn't change, a static entity 
 * is an entity that does not move, like a tree or rock
 * unlike creature entity like player
 * @author matthewluthi
 */
public abstract class StaticEntity extends Entity {
    public StaticEntity(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
    }
}
