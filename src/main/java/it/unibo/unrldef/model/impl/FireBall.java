package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.DefenseEntity;
import it.unibo.unrldef.model.api.Potion;

/**
 * A fireball potion used in a tower defense game
 * @author tommaso.severi2@studio.unibo.it
 */
public class FireBall extends DefenseEntity implements Potion{

    private final double full = 100.0;
    private final double rechargeRate = 10.0;
    private final double activationRate = 50.0;

    private double state;
    private boolean active;

    /**
     * Creates a new potion of type fireball 
     */
    public FireBall() {
        super(null, "fireball", 10.0, 30.0);
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

    public double getState() {
        return this.state;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void updateState() {
        if (!this.isWaiting()) {
            this.state = this.isReady() ? 0.0 : this.state+this.getRate();
            if (this.state == 0.0 && this.isActive()) {
                this.active = false;
            }
        }
    }

    /**
     * Checks wether the potion is waiting to be used or not
     * @return true if it's waiting, false otherwise
     */
    private boolean isWaiting() {
        return this.isReady() && !this.isActive();
    }

    /**
     * @return the right amount to add depending which state is the potion in
     */
    private double getRate() {
        return this.isActive() ? this.activationRate : this.rechargeRate;
    }

    /**
     * @return true if the potion is ready to be used or being rethrived, false otherwise
     */
    private boolean isReady() {
        return this.state >= this.full;
    }
}
