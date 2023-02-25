package it.unibo.unrldef.model.api;

import java.util.Objects;
import java.util.Optional;

import it.unibo.unrldef.common.Position;

/**
 * A model of a defensive type entity in a strategic game
 * @author tommaso.severi2@studio.unibo.it
 */
public abstract class DefenseEntity extends Entity {

    private final double radius;
    private final double damage;

    /**
     * Crates a new defensive entinty
     * @param position where it'll placed
     * @param name its name
     * @param radius the radius it can deal damage from
     * @param damage the damage it inflicts to an enemy
     */
    public DefenseEntity(Optional<Position> position, String name, double radius, double damage) {
        super(position, name);
        this.radius = Objects.requireNonNull(radius);
        this.damage = Objects.requireNonNull(damage);
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

    
}
