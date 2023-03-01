package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Tower;
import it.unibo.unrldef.model.api.World;

/**
 * A tower that can be placed in a world
 * @author tommaso.ceredi@studio.unibo.it
 */
public class TowerImpl extends DefenseEntity implements Tower {

    private final int cost;

    public TowerImpl(Optional<Position> position, String name, World parentWorld, double radius, double damage,
            double attackRate, final int cost) {
        super(position, name, parentWorld, radius, damage, attackRate);
        this.cost = cost;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    protected void attack() {
        this.getTargetedEnemies().get(0).reduceHealth(this.getDamage());
        if (this.getTargetedEnemies().size() >= 1) {
            this.getTargetedEnemies().get(1).reduceHealth(this.getDamage());
        }
    }

    @Override
    public void updateState(final long time) {
        this.incrementTime(time);
    }
}
