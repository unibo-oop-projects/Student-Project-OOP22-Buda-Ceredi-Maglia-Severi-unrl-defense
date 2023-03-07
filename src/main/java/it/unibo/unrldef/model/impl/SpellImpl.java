package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Spell;
import it.unibo.unrldef.model.api.World;

public class SpellImpl extends DefenseEntity implements Spell {

    private final double waitTime;
    private boolean active;

    /**
     * Creates a new potion of type fireball 
     */
    public SpellImpl(final String name, final World parentWorld, final double radius,
            final double damage, final double attackRate, final double waitTime) {
        super(Optional.empty(), name, parentWorld, radius, damage, attackRate);
        this.waitTime = waitTime;
        this.active = false;
    }

    @Override
    public boolean tryActivation(final Position position) {
        if (!this.isActive() && this.isReady()) {
            this.active = true;
            super.setPosition(position.getX(), position.getY());
            return true;
        }
        return false;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    protected void attack() {
        if (this.isActive()) {
            for (final Enemy e : this.getParentWorld().sorroundingEnemies(this.getPosition().get(), this.getRadius())) {
                e.reduceHealth(this.getDamage());
            }
        }
    }

    @Override
    public void updateState(final long time) {
        this.incrementTime(time);
        if (this.isActive()) {
            this.checkAttack();
        }
    }

    /**
     * @return true if the potion is ready to be used, false otherwise
     */
    private boolean isReady() {
        return this.getTimeSinceLastAction() >= this.waitTime;
    }
}
