package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.model.common.Position;
import it.unibo.unrldef.model.api.Potion;

/**
 * A fireball potion used in a tower defense game
 * @author tommaso.severi2@studio.unibo.it
 */
public class FireBall implements Potion{

    // Placeholders
    private static final Double DMG = 30.0;
    private static final int RAD = 10;
    private static final Double READY = 100.0;
    private static final Double RCHGRATE = 10.0;

    private final String name;
    private Double state;
    private Optional<Position> position;

    /**
     * Creates a new potion of type fireball 
     */
    public FireBall() {
        this.name = "fireball";
        this.state = 0.0;
        position = Optional.empty();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Double getDamagePerFrame() {
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
    public Optional<Position> getCurrentPosition() {
        return this.position;
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
