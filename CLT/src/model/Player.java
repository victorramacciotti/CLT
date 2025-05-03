package model;

public class Player {
    
    // Positional attributes
    private int positionX;
    private int positionY;
    
    // Player identification and appearance
    private Character character;
    private String spritePath;
    private String idleGif, kickGif, punchGif, walkGif, backwardsGif, jumpGif,  currentGif, comboGif;
    
    public Player(int positionX, int positionY, Character character, String spritePath) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.character = character;
        this.spritePath = spritePath;
        
        loadGifs(spritePath);
        currentGif = idleGif;  
    }
    
    private void loadGifs(String spritePath) {
    	idleGif = spritePath;
    	kickGif = spritePath.replace("idle", "kick");
        punchGif = spritePath.replace("idle", "punch");
        walkGif = spritePath.replace("idle", "walk"); 
        backwardsGif = spritePath.replace("idle", "backwards");
        jumpGif = spritePath.replace("idle", "jump");
        comboGif = spritePath.replace("idle", "combo");
    }
    
    public void setState(String state) {
        switch (state) {
            case "idle":
                currentGif = idleGif;
                break;
            case "walk":
                currentGif = walkGif;
                break;
            case "kick":
                currentGif = kickGif;
                break;
            case "punch":
                currentGif = punchGif;
                break;
            case "backwards":
                currentGif = backwardsGif;
                break;
            case "jump":
                currentGif = jumpGif;
                break;
            case "comboAttack":
                currentGif = comboGif;
                break;
        }
    }
    
    public void attack(Player opponent, String attackType) {
        float damage;
        
        switch (attackType.toLowerCase()) {
            case "punch":
                damage = character.punchAttack();
                break;
            case "kick":
                damage = character.kickAttack();
                break;
            case "combo":
                damage = character.comboAttack();
                break;
            default:
                throw new IllegalArgumentException("Invalid attack type: " + attackType);
        }
        
        System.out.println(character.getName() + " attacked " + opponent.getCharacter().getName() 
            + " with a " + attackType + " attack causing " + damage + " damage.");
        opponent.getCharacter().takingDamage(damage);
    }
    
    public boolean isAlive() {
        return character.getLife() > 0;
    }
    
    // Getters
    public int getPositionX() {
        return positionX;
    }
    
    public int getPositionY() {
        return positionY;
    }
    
    public Character getCharacter() {
        return character;
    }
    
    public String getSpritePath() {
        return spritePath;
    }
    
    public String getCurrentGif() {
        return currentGif;
    }
    
    // Setters
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    
    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
    }
    
    public void move(int x, int y) {
        this.positionX += x;
        this.positionY += y;
    }
}