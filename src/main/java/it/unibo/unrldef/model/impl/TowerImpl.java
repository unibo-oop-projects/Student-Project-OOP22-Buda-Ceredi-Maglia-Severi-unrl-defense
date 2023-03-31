package it.unibo.unrldef.model.impl;

import java.util.List;
import java.util.Optional;

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
    private Optional<Enemy> target = Optional.empty();

    public TowerImpl(String name, double radius, double damage,
            long attackRate, final int cost) {
        super( name, radius, damage, attackRate);
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
        this.incrementTime(time);
        this.checkAttack();
    }

    @Override
    protected void attack() {
        final List<Enemy> enemiesInRange = this.getParentWorld().sorroundingEnemies(this.getPosition().get(), this.getRadius());
        if (!enemiesInRange.isEmpty()) {
            if (this.target.isEmpty() || !enemiesInRange.contains(this.target.get())) {
                this.target = Optional.of(enemiesInRange.get(0));
            }
            this.target.get().reduceHealth(this.getDamage());
            this.additionAttack(this.target.get());
        } else {
            this.target = Optional.empty();
        }
    }

    @Override
    public Optional<Enemy> getTarget() {
        if (this.target != null) {
            return this.isAttacking() ? this.target : Optional.empty();
        }
        return Optional.empty();
    }

    protected abstract void additionAttack(final Enemy target);
}
