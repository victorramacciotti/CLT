package model;

public class Character {
    
    private String name;
    private float maxLife;
    private float life;
    private float strength;
    private final float WIDTH;
    private final float HEIGHT;

    public Character(String name, float maxLife, float strength, float defense, int width, int height) {
        this.name = name;
        this.maxLife = maxLife;
        this.life = maxLife;
        this.strength = strength;
        this.WIDTH = width;
        this.HEIGHT = height;
    }
    
    public float takingDamage(float damage) {
        this.life -= Math.max(damage, 0);
        if (this.life < 0) {
            this.life = 0;
        }
        return this.life;
    }

    public float calculateDamage(float baseStrength, float multiplier) {
        float damage = baseStrength * multiplier;
        return Math.max(damage, 0);
    }

    public float punchAttack() {
        return calculateDamage(strength, 1.0f);
    }
    
    public float kickAttack() {
        return calculateDamage(strength, 1.2f);
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public float getMaxLife() {
        return maxLife;
    }
    
    public float getLife() {
        return life;
    }
    
    public float getStrength() {
        return strength;
    }
    
    public float getWIDTH() {
		return WIDTH;
	}

	public float getHEIGHT() {
		return HEIGHT;
	}

	// Setters
    public void setLife(float life) {
        this.life = Math.max(life, 0);
    }
}