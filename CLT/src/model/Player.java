package model;

public class Player {
	
	private int positionX;
	private int positionY;
	private int width = 80;
	private int height = 120;
	
	private int playerID;
	private Character character;
	private String spritePath;
	
	public Player() {}
	
	public Player(int playerID, int positionX, int positionY) {
		this.playerID = playerID;
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	public Player(int playerID, Character personagem, String spritePath) {
		this.playerID = playerID;
		this.character = personagem;
		this.spritePath = spritePath;
	}
	
	public void attack(Player opponent, String attackType, boolean opponentDefensed) {
		float damage = 0;
		
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
			throw new IllegalArgumentException("Unexpected value: " + attackType);
		}
		
		System.out.println(character.getName() + " atacou " + opponent.getCharacter().getName() + " com um ataque " + attackType + " causando " + damage + " de dano.");
        opponent.getCharacter().takingDamage(damage);
	}
	
	public void move(int x, int y) {
		this.positionX += x;
		this.positionY += y;
	}
	
	public boolean isAlive() {
        return character.getLife() > 0;
    }

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}
	
	public int getWidth() {
		return positionX;
	}
	
	public int getHeight() {
		return positionY;
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

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public void setCharacter(Character personagem) {
		this.character = personagem;
	}
	
	public void setSpritePath(String spritePath) {
		this.spritePath = spritePath;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	
	
	
}
