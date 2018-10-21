package entities.projectiles;

import deadManWalking.Handler;
import entities.Entity;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import deadManWalking.tiles.Tile;

/**
 *
 * @author matthewluthi
 */
public class Bullet extends Entity {

    private float speed; //Velx vely
    private final float DEFAULT_SPEED = 10f;
    private boolean shootUp, shootDown, shootLeft, shootRight;
    //IF THE BULLET DID HIT ENTITY REMOVE FROM ENITYMANAGER from player checkAttack() method!!!!!!!!!!!!!!!!!!
    private boolean bulletHit;

    //These attributes relate to the bullet's attack proximity
    //because of the way we check entity movement, the bullet entity will stop
    //moving right before it hits an entity, essentially the bullet collision bounds 
    //will never intersect to hit a creature entity collision bounds, so we must draw
    //an attack rectangle to predict where the bullet will hit and check those new bounds if 
    //it intersects with creature bounds
    private Rectangle bulletAttack;
    //If a bullet is within 10px of another creature entity then it should be able to hit it
    private final int BULLET_ATTACK_SIZE = 10;
    //this is the same as inherited collision bounds of bullet, just local variable for easy calculations in x, yDirection
    private Rectangle bulletCb; 

    public Bullet(Handler handler, float x, float y) {
        super(handler, x, y, 8, 8);
        speed = DEFAULT_SPEED;
        bulletHit = false;
        
        bulletAttack = new Rectangle();
        bulletAttack.width = BULLET_ATTACK_SIZE;
        bulletAttack.height = BULLET_ATTACK_SIZE;
        
    }

    @Override
    public void tick() {
        //NEW CODE
        if (!checkEntityCollisions(speed, 0f)) {
            //if bullet does not collide with anything let it move
            xDirection();
        } else {
            //Once this bullet has hit another entity it will
            //be removed from entity list and destroyed
            bulletHit = true;
        }

        if (!checkEntityCollisions(0f, speed)) {
            //let it move y direction if x direction not applicable
            yDirection();
        } else {
            bulletHit = true;
        }

        //x += velX;
        //y += velY;
    }

    //SIMPLIFY BULLET DIRECTION AKIN TO PLAYER!!!!!!!!!!!!! NOT HAVING TO MANUALLY SET x += speed, x -= speed!!!!!///////////////
    public void xDirection() {
        if (shootRight) {
            //Use temporary x var to determine where the bullet will move to 
            //using bullet x-point, speed(where the bullet will go next), and bounds width(width of bullet entity by default)
            //then divide by tileWidth to get the tile co-ordinate. 
            int tempX = (int) (x + speed + bounds.width) / Tile.TILEWIDTH;
            //Check to see if bullet does not hit a solid tile, then let the bullet travel
            //NOTE 1st if statement checks upper right corner of bounding box && -> checks the lower right corner of bounding box
            if (!collisionWithTile(tempX, (int) (y + bounds.y) / Tile.TILEHEIGHT)
                    && !collisionWithTile(tempX, (int) (y + bounds.y + bounds.height / Tile.TILEHEIGHT))) {
                x += speed;

                bulletCb = getCollisionBounds(0,0);
                bulletAttack.x = bulletCb.x + bulletCb.width;
                bulletAttack.y = bulletCb.y + bulletCb.height / 2 - BULLET_ATTACK_SIZE / 2;    
            } else {
                //If there is a collision with a tile then set entity to inactive
                //so that the entityManager can remove the bullet from the game
                this.setActive(false);
            }
        } else if (shootLeft) {
            //Check leftSide of boundary box(so don't add bounds.width to get x-point)
            int tempX = (int) (x - speed) / Tile.TILEWIDTH;
            //Check to see if bullet does not hit a solid tile, then let the bullet travel
            //NOTE 1st if statement checks upper right corner of bounding box && -> checks the lower right corner of bounding box
            if (!collisionWithTile(tempX, (int) (y + bounds.y) / Tile.TILEHEIGHT)
                    && !collisionWithTile(tempX, (int) (y + bounds.y + bounds.height / Tile.TILEHEIGHT))) {
                x -= speed;
                
                bulletCb = getCollisionBounds(0,0);
                bulletAttack.x = bulletCb.x - BULLET_ATTACK_SIZE;
                bulletAttack.y = bulletCb.y + bulletCb.height / 2 - BULLET_ATTACK_SIZE / 2;
                
            } else {
                //If there is a collision with a tile then set entity to inactive
                //so that the entityManager can remove the bullet from the game
                this.setActive(false);
            }
        }

    }

    public void yDirection() {
        if (shootUp) { //Bullet moving up
            int tempY = (int) ((y - speed + bounds.y) / Tile.TILEHEIGHT);

            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, tempY)
                    && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, tempY)) {
                y -= speed;
                
                bulletCb = getCollisionBounds(0,0);
                bulletAttack.x = bulletCb.x + bulletCb.width / 2 - BULLET_ATTACK_SIZE / 2;
                bulletAttack.y = bulletCb.y - BULLET_ATTACK_SIZE;
            } else {
                this.setActive(false);
            }
        } else if (shootDown) { //Bullet moving down
            int tempY = (int) ((y + speed + bounds.y + bounds.height) / Tile.TILEHEIGHT);

            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, tempY)
                    && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, tempY)) {
                y += speed;
                
                bulletCb = getCollisionBounds(0,0);
                bulletAttack.x = bulletCb.x + bulletCb.width / 2 - BULLET_ATTACK_SIZE / 2;
                bulletAttack.y = bulletCb.y + bulletCb.height;
                
            } else {
                this.setActive(false);
            }
        }
    }

    //Should all entities possess this method, only player and bullet implement so far
    protected boolean collisionWithTile(int x, int y) {
        return handler.getWorld().getTile(x, y).isSolid();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval((int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), 8, 8);

        /*
        g.setColor(Color.YELLOW);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height);

        g.setColor(Color.CYAN);
        g.fillRect((int) (bulletAttack.x - handler.getGameCamera().getxOffset()), (int) (bulletAttack.y - handler.getGameCamera().getyOffset()), 10, 10);
        */
    }

    @Override
    public void die() {

    }

    public boolean getBulletHit() {
        return bulletHit;
    }

    public void setShootUp(boolean shootUp) {
        this.shootUp = shootUp;
    }

    public void setShootDown(boolean shootDown) {
        this.shootDown = shootDown;
    }

    public void setShootLeft(boolean shootLeft) {
        this.shootLeft = shootLeft;
    }

    public void setShootRight(boolean shootRight) {
        this.shootRight = shootRight;
    }

    public Rectangle getBulletAttack() {
        return bulletAttack;
    }
    
    

}
