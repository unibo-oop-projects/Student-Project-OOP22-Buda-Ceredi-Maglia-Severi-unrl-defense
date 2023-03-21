package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Spell;
import it.unibo.unrldef.model.api.World;

/**
 * Implememntation of a generic spell in a tower defense game
 * @author tommaso.severi2@studio.unibo.it
 */
public abstract class SpellImpl extends DefenseEntity implements Spell {

    private boolean active;
    private final long lingeringEffectTime;
    private final long lingeringEffectFrequency;
    private long lingerTime = 0;

    /**
     * Creates a new spell 
     * @param waitTime is the time the player have to wait before being able to use the spell again
     * @param lingeringDamage the number of times the spell deals its damage before fading away
     */
    public SpellImpl(final String name, final World parentWorld, final double radius,
            final double damage, final long attackRate, final long lingeringEffectTime, 
            final long lingeringEffectFrequency) {
        super(Optional.empty(), name, radius, damage, attackRate);
        this.setParentWorld(parentWorld);
        this.lingeringEffectTime = lingeringEffectTime;
        this.lingeringEffectFrequency = lingeringEffectFrequency;
        this.active = false;
    }

    @Override
    public boolean ifPossibleActivate(final Position position) {
        if (!this.isActive() && this.isReady()) {
            this.active = true;
            super.setPosition(position.getX(), position.getY());
            this.checkAttack();
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
        this.getParentWorld().sorroundingEnemies(this.getPosition().get(), this.getRadius())
                .forEach(e -> e.reduceHealth(this.getDamage()));
    }

    @Override
    public void updateState(final long time) {
        this.incrementTime(time);
        if (this.isActive()) {
            this.ifPossibleApplyEffect();
            this.lingerTime += time;
        }
    }

    /**
     * @return true if the spell is ready to be used, false otherwise
     */
    public boolean isReady() {
        return this.getTimeSinceLastAction() >= this.getAttackRate() && !this.isActive();
    }

    /**
     * Sets the spell back to its waiting state after dealing damage
     */
    private void deactivate() {
        this.active = false;
        this.resetElapsedTime();
        this.lingerTime = 0;
    }

    /**
     * Applies the affect of the spell to the enemies in range if possible
     */
    private void ifPossibleApplyEffect() {
        if (this.getTimeSinceLastAction() <= this.lingeringEffectTime) {
            if (this.lingerTime >= this.lingeringEffectFrequency) {
                System.out.println("Attack: " + this.lingerTime);
                this.getParentWorld().sorroundingEnemies(this.getPosition().get(), this.getRadius())
                    .forEach(e -> this.effect(e));
                this.lingerTime = 0;
            }
        } else {
            this.deactivate();
        }
    }

    /**
     * The effect of the spell while lingering
     */
    protected abstract void effect(final Enemy enemy);
}
