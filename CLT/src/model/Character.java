package model;

public class Character {
    
    private String name;
    private float maxLife;
    private float life;
    private float strength;
    private float defense;
    
    public Character() {
    }

    public Character(String name, float maxLife, float strength, float defense) {
        this.name = name;
        this.maxLife = maxLife;
        this.life = maxLife;
        this.strength = strength;
        this.defense = defense;
    }
    
    public float takingDamage(float damage) {
        this.life -= Math.max(damage, 0);
        if (this.life < 0) {
            this.life = 0;
        }
        return this.life;
    }

    public float calculateDamage(float baseStrength, float enemyDefense, boolean defensed, float multiplier) {
        float damage = baseStrength * multiplier;
        if (defensed) {
            damage -= enemyDefense;
        }
        return Math.max(damage, 0);
    }

    public float weakAttack(boolean defensed, float enemyDefense) {
        return calculateDamage(strength, enemyDefense, defensed, 1.0f);
    }
    
    public float strongAttack(boolean defensed, float enemyDefense) {
        return calculateDamage(strength, enemyDefense, defensed, 1.5f);
    }
    
    public float specialAttack(boolean defensed, float enemyDefense) {
        return calculateDamage(strength, enemyDefense, defensed, 2.0f);
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
    
    public float getDefense() {
        return defense;
    }
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setLife(float life) {
        this.life = Math.max(life, 0);
    }
    
    public void setStrength(float strength) {
        this.strength = strength;
    }
    
    public void setDefense(float defense) {
        this.defense = defense;
    }
}