package model;

public class Player {
    
    // Positional attributes
    private int positionX;
    private int positionY;
    private final int WIDTH = 80;
    private final int HEIGHT = 120;
    
    // Player identification and appearance
    private int playerID;
    private Character character;
    private String spritePath;

    public Player() {}
    
    public Player(int playerID, int positionX, int positionY) {
        this.playerID = playerID;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Player(int playerID, Character character, String spritePath) {
        this.playerID = playerID;
        this.character = character;
        this.spritePath = spritePath;
    }
    
    public Player(int playerID, int positionX, int positionY, Character character, String spritePath) {
        this.playerID = playerID;
        this.positionX = positionX;
        this.positionY = positionY;
        this.character = character;
        this.spritePath = spritePath;
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