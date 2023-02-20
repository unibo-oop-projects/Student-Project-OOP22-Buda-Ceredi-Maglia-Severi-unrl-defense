package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.Potion;

/**
 * A fireball potion used in a tower defense game
 * @author tommaso.severi2@studio.unibo.it
 */
public class FireBall extends Entity implements Potion{

    // Placeholders
    private static final Double DMG = 30.0;
    private static final int RAD = 10;
    private static final Double FULL = 100.0;
    private static final Double RCHGRATE = 10.0;
    private static final Double ACTVRATE = 50.0;

    private Double state;
    private boolean active;

    /**
     * Creates a new potion of type fireball 
     */
    public FireBall() {
        super(null, "fireball");
        this.active = false;
        this.state = 0.0;
    }

    @Override
    public boolean tryActivation(Position position) {
        if (!this.isActive()) {
            this.active = this.isReady();
            super.setPosition(this.active ? position : null);
            return this.active;
        }
        return false;
    }

    @Override
    public Double getDamagePerFrame() {
        return DMG;
    }

    @Override
    public Integer getRadius() {
        return RAD;
    }

    public Double getState() {
        return this.state;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void updateState() {
        this.state = this.isReady() ? 0.0 : this.state+this.getRate();
    }

    /**
     * @return the right amount to add depending which state is the potion in
     */
    private Double getRate() {
        return this.isActive() ? ACTVRATE : RCHGRATE;
    }

    /**
     * @return true if the potion is ready to be used or being rethrived, false otherwise
     */
    private boolean isReady() {
        return this.state >= FULL;
    }
}
