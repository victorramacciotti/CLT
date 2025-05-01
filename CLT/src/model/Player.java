package model;

import javax.swing.ImageIcon;

public class Player {
    
    // Positional attributes
    private int positionX;
    private int positionY;
    private final int WIDTH = 200;
    private final int HEIGHT = 239;
    
    // Player identification and appearance
    private int playerID;
    private Character character;
    private String spritePath;
    private String idleGif, kickGif, punchGif, walkGif, backwardsGif, jumpGif,  currentGif;

    public Player() {}
    
    public Player(int playerID, int positionX, int positionY, Character character, String spritePath) {
    	
        this.playerID = playerID;
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
        }
    }
    
    public void attack(Player opponent, String attackType, boolean opponentDefensed) {
        float damage;
        
        switch (attackType.toLowerCase()) {
            case "weak":
                damage = character.weakAttack(opponentDefensed, opponent.getCharacter().getDefense());
                break;
            case "strong":
                damage = character.strongAttack(opponentDefensed, opponent.getCharacter().getDefense());
                break;
            case "special":
                damage = character.specialAttack(opponentDefensed, opponent.getCharacter().getDefense());
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
    
    public int getWidth() {
        return WIDTH;
    }
    
    public int getHeight() {
        return HEIGHT;
    }
    
    public int getPlayerID() {
        return playerID;
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
    
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
    
    public void setCharacter(Character character) {
        this.character = character;
    }
    
    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
    }
    
    public void move(int x, int y) {
        this.positionX += x;
        this.positionY += y;
    }
}