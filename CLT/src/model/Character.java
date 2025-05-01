package model;

public class Character {
    
    private String name;
    private float maxLife;
    private float life;
    private float strength;
    
    public Character() {
    }

    public Character(String name, float maxLife, float strength, float defense) {
        this.name = name;
        this.maxLife = maxLife;
        this.life = maxLife;
        this.strength = strength;
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
    
    // Setters
    public void setLife(float life) {
        this.life = Math.max(life, 0);
    }
}