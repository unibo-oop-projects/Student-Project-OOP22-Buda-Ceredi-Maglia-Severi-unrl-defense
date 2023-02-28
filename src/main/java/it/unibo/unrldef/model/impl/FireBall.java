package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Potion;

/**
 * A fireball potion used in a tower defense game
 * @author tommaso.severi2@studio.unibo.it
 */
public class FireBall extends DefenseEntity implements Potion{

    private static final String NAME = "fireball";
    private static final double ATTACK_RATE = 0.0;
    private static final double DMG = 30.0;
    private static final double RAD = 10.0;
    private final double ready = 10.0;
    private boolean active;

    /**
     * Creates a new potion of type fireball 
     */
    public FireBall() {
        super(null, FireBall.NAME, FireBall.RAD, FireBall.DMG, FireBall.ATTACK_RATE);
        this.active = false;
    }

    @Override
    public boolean tryActivation(final Position position) {
        if (!this.isActive() && this.isReady()) {
            this.active = true;
            super.setPosition(Optional.of(position));
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
            for (final Enemy e : this.getTargetedEnemies()) {
                if (e.getHealth() > 0) {
                    e.rreduceHealth(this.getDamage());
                }
            }
        }
    }

    @Override
    public void updateState() {
        this.updateTimer();
        if (this.isActive()) {
            this.checkAttack();
        }
    }

    /**
     * @return true if the potion is ready to be used, false otherwise
     */
    private boolean isReady() {
        return this.getTimer() >= this.ready;
    }
}
