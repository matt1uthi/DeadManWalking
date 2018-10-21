package entities;

import deadManWalking.Game;
import deadManWalking.Handler;
import entities.creatures.Creature;
import entities.creatures.Player;
import entities.projectiles.Bullet;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author matthewluthi
 */
public class EntityManager {
    //Create local Entity var so that it can be added to entities arrayList
    //after the tick method is finished traversing the list(otherwise it generates
    //concurrentModification exception)
    private Entity entityToBeAdded = null;
    //secondary list that has the same application as above variable however
    //adds multiple entities at any given time
    private ArrayList<Entity> entitiesToBeAdded;
    
    private Handler handler;
    private Player player;
    //Main list of entityManager
    private ArrayList<Entity> entities;
    //If we want the player to appear above the tree at the bottom and behind the tree 
    //at the tip of tree(already is) then we have to compare entities in list
    //and change the order they render, player must be render after tree to appear above tree
    private Comparator<Entity> renderSorter = new Comparator<Entity>() {
        @Override
        public int compare(Entity a, Entity b) {
            //Return -1 if a should be rendered before b
            //return 1 if a should be rendered after b
            if ((a.getY() + a.getHeight()) < (b.getY() + b.getHeight())) {
                //if y co-ord of a is less than b y co-ord ie. a has a lesser
                //y component so it is above b
                return -1;
            } else if ((a.getY() + a.getHealth()) > (b.getY() + b.getHeight())) {
                return 1;
            } 
            //return 1;
            return 0;
        }
    };

    public EntityManager(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<Entity>();
        //Add player entity to list but still need seperate instance to access easily
        addEntity(player);
        
        entitiesToBeAdded = new ArrayList<Entity>();
    }

    //The problem is we are not able to add the bullet while
    //the entities arraylist is being traversed using Iterator
    public void tick() {
        //Looping through entities array
        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            //System.out.println("Ticking");
            Entity e = iterator.next();
            e.tick();
            if (!e.isActive()) {
                //safely remove an entity without skipping over other entities
                iterator.remove();
            }      
        }
        
        if (entityToBeAdded != null) {
            addEntity(entityToBeAdded);
            entityToBeAdded = null;
        }
        
        if (entitiesToBeAdded != null) {
            //add all the entities in the list
            for (int i = 0; i < entitiesToBeAdded.size(); i ++) {
                addEntity(entitiesToBeAdded.get(i));
            }
            
            //then after all the entities have been added to the main entities arrayList
            //remove all of them from the secondary list, so that when a newRound in Game is called
            //it will add the correct number of creatures to the secondary list
            for (int i = entitiesToBeAdded.size() - 1; i >= 0; i--) {
                entitiesToBeAdded.remove(i);
            }
        }
       
        //This is bad because it removes entity while still looping
        //which stops some entities from running their tick updates
        /*for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            //Update current entity
            e.tick();
            
            //If entity is dead and no longer active remove from list
            if (!e.isActive()) {
                entities.remove(e);
            }
        }*/
        //After updating positions(tick) of entities we need to sort the array
        entities.sort(renderSorter);
    }

    public void render(Graphics g) {
        for (Entity e : entities) {
            e.render(g);
        }
        //in future may have to add postRender to all entities
        //call post render of player after every other entity has
        //been rendered so that the inventory will appear on top
        //of everything else
        player.postRender(g);
    }
    
    //When we want to add a moving entity like a bullet, we must
    //wait for all the entities in entityManager to tick first, then
    //when the iterator is done, we can add the local entity var in this.tick()
    public void addMobileEntity(Entity e) {
        entityToBeAdded = e;
    }
    
    
    /**
     * Special method that adds an entity to local variable, the difference is 
     * that it is added to an arrayList() because when this method gets called
     * from the game class it will add multiple entities in a single game tick so we
     * need to save this in a list for the multiple entity objects that will be added
     * in this.tick() (entityManager)
     * @param e an entity
     */
    public void addCreatureEntities(Entity e) {
        entitiesToBeAdded.add(e);
        
    }

    //Take an entity we pass and add to arraylist so it can be ticked and render
    public void addEntity(Entity e) {
        entities.add(e);
      
        
        //System.out.println("Entity added:"+e.getClass().getSimpleName());
        /*if (entities != null) {
            List<Entity> entitiesToBeAdded = new ArrayList<Entity>();
            Iterator<Entity> it = entities.iterator();

            while (it.hasNext()) {
                Entity entity = it.next();
                entitiesToBeAdded.add(entity);
            }
            entitiesToBeAdded.add(e);
            entities.addAll(entitiesToBeAdded);
        }*/
    }
    
    /**
     * Get number of enemies left in the game at this moment
     * @return number of enemies
     */
    public int getEnemies() {
        int count = 0;
        
        for (Entity e : entities) {
            if (e.equals(player)) {
                continue;
            }
            
            if (e instanceof Creature) {
                count++;
            }
        }
        
        return count;
    }

    public Handler getHandler() {
        return handler;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

}
