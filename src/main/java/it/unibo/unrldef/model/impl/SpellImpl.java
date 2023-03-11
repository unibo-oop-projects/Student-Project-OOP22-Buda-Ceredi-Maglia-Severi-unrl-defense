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
public class SpellImpl extends DefenseEntity implements Spell {

    private final double waitTime;
    private boolean active;
    private final int lingeringDamage;
    private int damageDealt;

    /**
     * Creates a new spell 
     * @param waitTime is the time the player have to wait before being able to use the spell again
     * @param lingeringDamage the number of times the spell deals its damage before fading away
     */
    public SpellImpl(final String name, final World parentWorld, final double radius,
            final double damage, final long attackRate, final long waitTime, final int lingeringDamage) {
        super(Optional.empty(), name, radius, damage, attackRate);
        this.waitTime = waitTime;
        this.active = false;
        this.lingeringDamage = lingeringDamage;
        this.damageDealt = 0;
        this.setParentWorld(parentWorld);
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
        if (this.damageDealt < this.lingeringDamage) {
            for (final Enemy e : this.getParentWorld().sorroundingEnemies(this.getPosition().get(), this.getRadius())) {
                e.reduceHealth(this.getDamage());
            }
            this.damageDealt++;
        } else {
            this.deactivate();
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
     * @return true if the spell is ready to be used, false otherwise
     */
    private boolean isReady() {
        return this.getTimeSinceLastAction() >= this.waitTime;
    }

    /**
     * Sets the spell back to its waiting state after dealing damage
     */
    private void deactivate() {
        this.active = false;
        this.damageDealt = 0;
    }
}
