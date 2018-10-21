package states;

import java.awt.Graphics;
import deadManWalking.Game;
import deadManWalking.Handler;

/**
 * RULES MENU, GAMEOVER MENU(button to return to mainmenu +display stats)... In
 * a game there is MainMenu, Settings, Game(User begins playing) in game
 * programming these are called states where if user is at mainMenu and wants to
 * play game(start) they will go to the Game state etc
 *
 * @author matthewluthi
 */
public abstract class State {

    //We need a game state manager so we can change states
    //and tells us what state we should currently be on to tick & render
    //on that particular state
    private static State currentState = null;//Our current state

    /**
     * If we call the setState method from anywhere in the game it will change
     * what the current state ticks and renders in when showing it to the screen
     *
     * @param state The state we want to change to
     */
    public static void setState(State state) {
        currentState = state;
    }

    //These methods are static and can be accessed from anywhere and has
    //nothing to do with this abstract class (ie this code is a seperate class)
    public static State getState() {
        return currentState;
    }

    protected Handler handler;
    //Because we initialize states in game.init() once
    //the uiManager of the handler object gets set to null
    //so we need a way of telling if we want to switch states
    //then we must create a new instance of the state object to
    //pass an updated version of handler(ie, game.uiManager) to tick
    //in the new state
    private boolean switchState;

    public State(Handler handler) {
        this.handler = handler;
        switchState = false;
    }

    //CLASS
    //Each state must have a tick and render method
    public abstract void tick();

    //We must pass the magical paintbrush to the render method of each state
    public abstract void render(Graphics g);

    public boolean isSwitchState() {
        return switchState;
    }

    public void setSwitchState(boolean switchState) {
        this.switchState = switchState;
    }

}
