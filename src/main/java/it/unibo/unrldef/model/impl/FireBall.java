package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.model.api.Potion;

public class FireBall implements Potion{

    // Placeholders
    private static final Double DMG = 30.0;
    private static final int RAD = 10;
    private static final Double READY = 100.0;
    private static final Double RCHGRATE = 10.0;

    private final String name;
    private Double state;

    public FireBall() {
        this.name = "fireball";
        this.state = 0.0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Double getDamagePerTick() {
        return DMG;
    }

    @Override
    public Integer getRadius() {
        return RAD;
    }

    @Override
    public Double getRechargeState() {
        return this.state;
    }

    @Override
    public boolean isReady() {
        return this.state == READY;
    }

    @Override
    public void updateState() {
        this.state = this.isReady() ? 0.0 : this.state+RCHGRATE; 
    }
}
