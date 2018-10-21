package states;

import deadManWalking.Game;
import java.awt.Graphics;
import deadManWalking.Handler;
import gfx.Assets;
import gfx.Text;
import java.awt.Color;
import java.awt.Font;
import deadManWalking.worlds.World;

/**
 * Most used state with all the gameplay
 *
 * @author matthewluthi
 */
public class GameState extends State {

    private World world;

    private int count = 0;

    public GameState(Handler handler) {
        super(handler);
        world = new World(handler, "res/worlds/world2.txt");
        handler.setWorld(world);

        //game.getGameCamera().move(0, 0);
    }

    @Override
    public void tick() {
        world.tick();
        //game.getGameCamera().move(1, 1);
    }

    @Override
    public void render(Graphics g) {
        //player will appear on top of the world
        world.render(g);
        //Tile.tiles[2].render(g, 0, 0);

        //If the player is looking at the inventory then do not render HUD to screen
        if (!handler.getWorld().getEntityManager().getPlayer().getInventory().isActive()) {
            String ammo = Integer.toString(handler.getWorld().getEntityManager().getPlayer().getAmmo());
            String maxAmmo = Integer.toString(handler.getWorld().getEntityManager().getPlayer().getMaxAmmo());
            Text.drawString(g, ammo + "/" + maxAmmo, 490, 310, false, Color.white, Assets.font28); //490/340

            String round = "Round " + Integer.toString(Game.ROUND);
            Text.drawString(g, round, 20, 340, false, new Color(153, 4, 19), Assets.font28);
            
            String points = "Points "+Integer.toString(handler.getWorld().getEntityManager().getPlayer().getPoints());
            Text.drawString(g, points, 400, 340, false, Color.YELLOW, Assets.font28);
            
            String enemies = "Enemies left: "+Integer.toString(handler.getWorld().getEntityManager().getEnemies());
            Text.drawString(g, enemies, 360, 25, false, Color.LIGHT_GRAY, Assets.font28);
        }
       
        /*
        //drawString IS SLOOOOOOOOOWWWWWWWWWW?? loading fonts
        String ammo = Integer.toString(handler.getWorld().getEntityManager().getPlayer().getAmmo());
        String maxAmmo = Integer.toString(handler.getWorld().getEntityManager().getPlayer().getMaxAmmo());
        g.setFont(new Font("Courier", Font.BOLD, 20)); //Verdana courier
        g.setColor(Color.WHITE);
        g.drawString(ammo + "/" + maxAmmo, 520, 340);

        g.setFont(new Font("Verdana", Font.BOLD, 30));
        g.setColor(new Color(153, 4, 19));
        g.drawString(Integer.toString(Game.ROUND), 20, 340);
         */
    }
}
