package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Tower;
import it.unibo.unrldef.model.api.World;

/**
 * A tower that can be placed in a world
 * @author tommaso.ceredi@studio.unibo.it
 */
public abstract class TowerImpl extends DefenseEntity implements Tower {

    private final int cost;
    private static World PARENT_WORLD;

    public TowerImpl(Position position, String name, double radius, double damage,
            long attackRate, final int cost) {
        super(Optional.of(position), name, PARENT_WORLD, radius, damage, attackRate);
        this.cost = cost;
    }

    public void setParentWorld(final World world) {
        PARENT_WORLD = world;
    }

    public abstract Tower copy();

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public void updateState(final long time) {
        this.incrementTime(time);
    }
}
