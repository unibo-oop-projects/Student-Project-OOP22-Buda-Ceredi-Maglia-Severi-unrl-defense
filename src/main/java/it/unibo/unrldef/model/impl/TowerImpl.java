package it.unibo.unrldef.model.impl;

import java.util.List;
import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Tower;
import it.unibo.unrldef.model.api.World;

/**
 * A tower that can be placed in a world
 * @author tommaso.ceredi@studio.unibo.it
 */
public class TowerImpl extends DefenseEntity implements Tower {

    private final int cost;
    private Enemy target;

    public TowerImpl(Position position, String name, World parentWorld, double radius, double damage,
            double attackRate, final int cost) {
        super(Optional.of(position), name, parentWorld, radius, damage, attackRate);
        this.cost = cost;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    protected void attack() {
        final List<Enemy> enemiesInRange = this.getParentWorld().sorroundingEnemies(this.getPosition().get(), this.getRadius());
        if (!enemiesInRange.contains(this.target)) {
            this.target = enemiesInRange.get(0);
        }
        if (!enemiesInRange.isEmpty()) {
            this.target.reduceHealth(this.getDamage());
        }
    }

    @Override
    public void updateState(final long time) {
        this.incrementTime(time);
    }
}
