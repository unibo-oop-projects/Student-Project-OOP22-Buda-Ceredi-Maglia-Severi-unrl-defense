package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Tower;
import it.unibo.unrldef.model.api.World;

/**
 * A tower that can be placed in a world
 * @author tommaso.ceredi@studio.unibo.it
 */
public abstract class TowerImpl extends DefenseEntity implements Tower {

    private final int cost;
    private World parentWorld;

    public TowerImpl(Position position, String name, double radius, double damage,
            long attackRate, final int cost) {
        super(Optional.ofNullable(position), name, radius, damage, attackRate);
        this.cost = cost;
        this.setParentWorld(parentWorld);
    }

    public abstract Tower copy();

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public void updateState(final long time) {
        System.out.println(this.getTimeSinceLastAction() + " + " + time + " = " + (this.getTimeSinceLastAction() + time));
        this.incrementTime(time);
        this.checkAttack();
    }

    public abstract Optional<Enemy> getTarget();
}
