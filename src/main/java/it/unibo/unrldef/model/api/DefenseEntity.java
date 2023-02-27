package it.unibo.unrldef.model.api;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;

/**
 * A model of a defensive type entity in a strategic game
 * @author tommaso.severi2@studio.unibo.it
 * @author tommaso.ceredi@studio.unibo.it
 */
public abstract class DefenseEntity extends Entity {

    private final double radius;
    private final double damage;
    private final double attackRate;
    private final List<Enemy> targetedEnemies;

    /**
     * Crates a new defensive entinty
     * @param position where it'll placed
     * @param name its name
     * @param radius the radius it can deal damage from
     * @param damage the damage it inflicts to an enemy
     * @param attackRate the rate at which it attacks
     */
    public DefenseEntity(Optional<Position> position, String name, double radius, double damage, double attackRate) {
        super(position, name);
        this.radius = Objects.requireNonNull(radius);
        this.damage = Objects.requireNonNull(damage);
        this.attackRate = Objects.requireNonNull(attackRate);
    }
    
    /**
     * @return the radius of the defensive entity
     */
    public double getRadius() {
        return this.radius;
    }

    /**
     * @return the damage of the defensive entity
     */
    public double getDamage() {
        return this.damage;
    }

    /**
     * checks if the entity can attack
     */
    public void checkAttack(){
        if (this.getTimeBeforeAttack() >= this.attackRate && !this.targetedEnemies.isEmpty()){
            this.resetTimer();
            this.attack();
        }
    }

    /**
     * this method is called when is time to attack
     */
    protected abstract void attack();
    
    /**
     * Sets the enemies in range
     */
    public void setEnemiesInRange(List<Enemy> enemies){
        this.targetedEnemies = enemies;
    }
}
