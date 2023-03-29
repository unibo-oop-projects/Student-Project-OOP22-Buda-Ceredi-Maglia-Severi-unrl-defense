package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Tower;
import it.unibo.unrldef.model.api.World;

/**
 * A tower that can be placed in a world.
 * 
 * @author tommaso.ceredi@studio.unibo.it
 */
public abstract class TowerImpl extends DefenseEntity implements Tower {

    private final int cost;
    private World parentWorld;

    /**
     * Builds a new Tower.
     * 
     * @param position   the position of the tower
     * @param name       the name of the tower
     * @param radius     the radius of the tower
     * @param damage     the damage of the tower
     * @param attackRate the attack rate of the tower
     * @param cost       the cost of the tower
     */
    public TowerImpl(final Position position, final String name, final double radius, final double damage,
            final long attackRate, final int cost) {
        super(Optional.ofNullable(position), name, radius, damage, attackRate);
        this.cost = cost;
        this.setParentWorld(parentWorld);
    }

    @Override
    public abstract Tower copy();

    @Override
    public final int getCost() {
        return this.cost;
    }

    @Override
    public final void updateState(final long time) {
        this.incrementTime(time);
        this.checkAttack();
    }

    @Override
    public abstract Optional<Enemy> getTarget();
}
