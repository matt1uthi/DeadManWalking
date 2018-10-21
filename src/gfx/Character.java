package gfx;

/**
 *At the main menu the player will be able to select
 * a character, character selection determines what cosmetic
 * skin gets rendered to the player object
 * @author matthewluthi
 */
public enum Character {
    KNIGHT("Knight", "\"Rest in pieces, zombies...\""), 
    GRIM_REAPER("Grim Reaper", "\"You don't want my soul, I don't have one\""),
    NINJA("Ninja", "\"A true warrior needs no weapon\"");
    
    private String name;
    private String quote;
    
    private Character(String name, String quote) {
        this.name = name;
        this.quote = quote;
    }
    
    public String getName() {
        return name;
    }
}
