package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Entity;

/**
 * Implementation of an Enemy in the Unreal Defense game
 * @author danilo.maglia@studio.unibo.it
 */
public class EnemyImpl extends Entity implements Enemy {
    private int health;
    private final int speed;

    public EnemyImpl(final Optional<Position> position, final String name, final int startingHealth, final int speed) {
        super(position, name);
        this.health = startingHealth;
        this.speed = speed;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void reduceHealth(final int amount) {
        this.health -= amount;
    }

    @Override
    public boolean isDead() {
        return this.getHealth() <= 0;
    }

    @Override
    public void updateState() {
        /*
         * TODO:
         * - Enemy movement in the game map
         */    
    }
    
}
