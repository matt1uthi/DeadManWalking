package entities.creatures;

import deadManWalking.Game;
import gfx.Animation;
import gfx.Assets;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import deadManWalking.Handler;
import deadManWalking.database.DBOperations;
import deadManWalking.inventory.Inventory;
import entities.Entity;
import entities.projectiles.Bullet;
import entities.statics.Headstone;
import entities.statics.Hitmarker;
import java.awt.Rectangle;

/**
 *
 * @author matthewluthi
 */
public class Player extends Creature {

    private final int maxAmmo = 100;
    private int ammo;
    private int points;

    //Animations
    private Animation animDown, animUp, animLeft, animRight,
            animAttackUp, animAttackDown, animAttackLeft, animAttackRight,
            animShootDown, animShootLeft, animShootRight;

    //Attack timer to limit the speed at which a player can attack so it isn't instant as ticks are very fast
    private long lastAttackTimer, attackCooldown = 150, attackTimer = attackCooldown; //attack cooldown 800 : 200
    private long lastHitmarker, drawHitmarker = 1000, hitmarkerTimer = 0;

    //Inventory
    private Inventory inventory;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        health = 1;

        //Bounds rectangle relative to the player
        //x is how far over from the edge of players image should bounding box be    
        bounds.x = 16;
        //y is how far down from the top edge of players image bounding box is
        bounds.y = 32;
        bounds.width = 32;
        bounds.height = 32;
        //All we have to do is check collision box co-ordinates and check
        //corner is touching solid tile ie if player moving down check lower
        //two corners of collision box

        //Animations
        //1000milisec in 1 sec so half a second
        animDown = new Animation(400, Assets.player_down);//500
        animUp = new Animation(400, Assets.player_up);
        animLeft = new Animation(400, Assets.player_left); //200 for zammy
        animRight = new Animation(400, Assets.player_right); //200

        animAttackDown = new Animation(200, Assets.attack_down);
        animAttackUp = new Animation(200, Assets.attack_up);
        animAttackLeft = new Animation(200, Assets.attack_left);
        animAttackRight = new Animation(200, Assets.attack_right);

        animShootDown = new Animation(200, Assets.shoot_down);
        animShootLeft = new Animation(200, Assets.shoot_left);
        animShootRight = new Animation(200, Assets.shoot_right);

        inventory = new Inventory(handler);

        setPoints(0);
        ammo = 100;
    }

    //Update any variables for an object(Move our player)
    @Override
    public void tick() {
        checkPlayerHealth();

        //Animations
        animDown.tick(); //increment index (tick)
        animUp.tick();
        animLeft.tick();
        animRight.tick();

        animAttackDown.tick();
        animAttackUp.tick();
        animAttackLeft.tick();
        animAttackRight.tick();
        animShootDown.tick();
        animShootRight.tick();
        animShootLeft.tick();

        //Movement
        getInput();
        move();
        handler.getGameCamera().centerOnEntity(this);
        /*if (game.getKeyManager().up) {
            //subtract y co-ordinate to move up
            y -= 3;
        }
        if (game.getKeyManager().down) {
            y += 3;s
        }
        if (game.getKeyManager().left) {
            x -= 3;
        }
        if (game.getKeyManager().right) {
            x += 3;
        }*/

        //Attack
        checkAttacks();

        //Inventory
        inventory.tick();
    }

    private void checkPlayerHealth() {
        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this)) {
                continue;
            }
            if (e instanceof Creature) {
                if (e.getCollisionBounds(0, 0).intersects(this.getCollisionBounds(xMove, yMove))) {
                    //Hurt player
                    this.hurt(1);
                    //System.out.println(health);
                    return;
                }
            }
        }
    }

    /**
     * check if user is pressing attack key and then generate actual attack we
     * are gonna do
     */
    private void checkAttacks() {
        //update timer
        attackTimer += System.currentTimeMillis() - lastAttackTimer; //elapsed time between these two calls
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown) {
            //if attackTimer has not been running for 800ms or more
            //then the player has to wait so skip the code below
            return;
        }

        //In the entity looper further down this method we generate a hitmarker
        //using that entity's position, so the next time we call this check attack method
        //we want to remove the hitmarker, so that it only displays briefly
        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e instanceof Hitmarker) {
                e.hurt(10);
            }
        }

        //After the cooldown code so we don't mess up timing
        //If inventory is showing then we don't want the player to attack
        if (inventory.isActive()) {
            return;
        }

        //Check direction of player planning to attack
        //gets co-ord of collision box of player
        Rectangle cb = getCollisionBounds(0, 0);
        //cb is collisionBox

        //Generate small attack rectangle(ar) extending player's collision box
        Rectangle ar = new Rectangle();
        //size of red rectangle, if entity is within 20px then
        //player should be able to attack
        int arSize = 20;
        ar.width = arSize;
        ar.height = arSize;

        //Check where to draw attack
        //direction of arrow determines attack
        //If single attack key you just check if key being pressed
        //and if statements of the direction of the player
        if (handler.getKeyManager().aUp) {
            //Attacking upwards, draw it properly above collisionbox
            //this gets the attack box(range) x co-ord
            ar.x = cb.x + cb.width / 2 - arSize / 2;
            ar.y = cb.y - arSize;
        } else if (handler.getKeyManager().aDown) {
            ar.x = cb.x + cb.width / 2 - arSize / 2;
            ar.y = cb.y + cb.height;
        } else if (handler.getKeyManager().aLeft) {
            ar.x = cb.x - arSize;
            ar.y = cb.y + cb.height / 2 - arSize / 2;
        } else if (handler.getKeyManager().aRight) {
            ar.x = cb.x + cb.width;
            ar.y = cb.y + cb.height / 2 - arSize / 2;
        }
        /*else {
            //If none of these if statements run, then return nothing
            //because player isn't attacking at all
            //return;
        }*/

        //Create bullet object but only add to entityManager if player actually wants to shoot
        Bullet bullet = new Bullet(handler, handler.getWorld().getEntityManager().getPlayer().getX() + handler.getWorld().getEntityManager().getPlayer().getWidth() / 2,
                handler.getWorld().getEntityManager().getPlayer().getY() + handler.getWorld().getEntityManager().getPlayer().getHeight() / 2);;
        //Check for shoot attack, make sure player has enough ammo
        if (ammo > 0) {
            if (handler.getKeyManager().shoot) {
                //Determine where to shoot based on direction player is facing
                if (handler.getKeyManager().up) {
                    //addMobileEntity special method for anything that we add to the game WHILE the game loads(running and ticking)
                    handler.getWorld().getEntityManager().addMobileEntity(bullet);
                    //Because we want to stop the bullet from colliding with the player's collision bounds
                    //we set x or y pos dependning on bullet direction to be ahead of the player
                    bullet.setY(bullet.getY() - 25);
                    bullet.setShootUp(true);
                    ammo--;
                } else if (handler.getKeyManager().down) {
                    handler.getWorld().getEntityManager().addMobileEntity(bullet);
                    //Add 25 px to bullet y position so that player does not collide with bullet
                    bullet.setY(bullet.getY() + 35);
                    bullet.setShootDown(true);
                    ammo--;
                } else if (handler.getKeyManager().left) {
                    handler.getWorld().getEntityManager().addMobileEntity(bullet);
                    bullet.setX(bullet.getX() - 40);
                    bullet.setShootLeft(true);
                    ammo--;
                } else if (handler.getKeyManager().right) {
                    handler.getWorld().getEntityManager().addMobileEntity(bullet);
                    bullet.setX(bullet.getX() + 25);
                    bullet.setShootRight(true);
                    ammo--;
                } else {
                    bullet = null;
                    return; //Return from method if not shooting
                }
            }
        }

        //If we know the player wants to attack something, then reset timer
        //because the player will attack the entity below
        attackTimer = 0;

        //if player attacks and we draw attack co-ordinates
        //Bullet tempBullet = null;
        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this) || e instanceof Bullet) { //|| e instanceof Bullet
                //If the current entity we are looping through is ourself
                //skip below code and run next iteration
                continue;
            }

            if (e.getCollisionBounds(0, 0).intersects(ar)) {
                handler.getWorld().getEntityManager().addMobileEntity(new Hitmarker(handler, e.getCollisionBounds(0, 0).x, e.getCollisionBounds(0, 0).y));
                //hurt entity with value of 1
                e.hurt(1);
                return; //only hurt one enitity at a time, and
                //wait for next tick method so return from this method
            }

            //We need a second entity looper to check if a bullet's collision bounds intersects with
            //a creature entity bound from above entity looper
            for (Entity e2 : handler.getWorld().getEntityManager().getEntities()) {
                if (e2.equals(this)) {
                    continue;
                }

                if (e2 instanceof Bullet) {
                    Bullet tempBullet = (Bullet) e2;

                    if (e.getCollisionBounds(0, 0).intersects(tempBullet.getBulletAttack())) {
                        e2.setActive(false);
                        //if the entity is creature then perform damage, leave static entities alone
                        if (e instanceof Creature) {
                            //display hitmarker at entity's collision bounds position
                            handler.getWorld().getEntityManager().addMobileEntity(new Hitmarker(handler,
                                    e.getCollisionBounds(0, 0).x, e.getCollisionBounds(0, 0).y));
                            e.hurt(1);
                            setPoints(getPoints() + 100);
                        }

                        return;
                    }
                }
            }
        }
    }

    @Override
    public void die() {
        System.out.println("You lose");
        handler.getWorld().getEntityManager().addMobileEntity(new Headstone(handler, x, y));

        String query = "Select * from Matt.Leaderboard";
        
        //BEFORE player dies save progress to database
        DBOperations dbOperations = new DBOperations(handler);
        dbOperations.establishConnection();
        dbOperations.createTable();
    }

    private void getInput() {
        //Reset moves back to 0 every time
        xMove = 0;
        yMove = 0;

        //Below reset values so out player has a chance to 
        //stop moving, we check if inventory is showing
        if (inventory.isActive()) {
            return;
        }

        if (handler.getKeyManager().up) {
            //Subtract from y-coord to move up
            yMove = -speed;
        }
        if (handler.getKeyManager().down) {
            yMove = speed;
        }
        if (handler.getKeyManager().left) {
            //x-axis, left is subtracting
            xMove = -speed;
        }
        if (handler.getKeyManager().right) {
            xMove = speed;
        }
    }

    @Override
    public void render(Graphics g) {
        //was g.drawImage Assets.player, 
        g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

        //Draw COLLISION BOX
//        g.setColor(Color.red);
//        //Subtract game camera's offset to get proper position but also x boundary
//        //co-ordinate is the offset(pixels) from current x position so must add it
//        g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
//                (int) (y + bounds.y - handler.getGameCamera().getyOffset()),
//                bounds.width, bounds.height);
    }

    //Is just like regular render method, but everything
    //will already be drawn to screen, SEPERATE INV render
    public void postRender(Graphics g) {
        //Inventory, Note inventory tick and render methods already
        //check if the player really wants to see inventory
        inventory.render(g);

    }

    //So we don't have to hardcode it in render
    private BufferedImage getCurrentAnimationFrame() {
        if (handler.getKeyManager().aUp) {
            return animAttackUp.getCurrentFrame();
        } else if (handler.getKeyManager().aDown) {
            return animAttackDown.getCurrentFrame();
        } else if (handler.getKeyManager().aLeft) {
            return animAttackLeft.getCurrentFrame();
        } else if (handler.getKeyManager().aRight) {
            return animAttackRight.getCurrentFrame();
        }

        if (handler.getKeyManager().shoot) {
            if (handler.getKeyManager().down) {
                return animShootDown.getCurrentFrame();
            } else if (handler.getKeyManager().left) {
                return animShootLeft.getCurrentFrame();
            } else if (handler.getKeyManager().right) {
                return animShootRight.getCurrentFrame();
            }
        }

        //Display certain image depending on where we are moving
        if (xMove < 0) { //Moving left because less than 0
            return animLeft.getCurrentFrame();
        } else if (xMove > 0) {
            return animRight.getCurrentFrame();
        } else if (yMove < 0) {
            return animUp.getCurrentFrame();
        } else if (yMove > 0) {
            return animDown.getCurrentFrame();
        } else {
            //Can default to standing animation
            return animDown.getCurrentFrame();
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
