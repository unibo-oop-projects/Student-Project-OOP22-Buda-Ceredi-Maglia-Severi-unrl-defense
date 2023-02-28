package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.model.api.World;

/**
 * A fireball potion used in a tower defense game
 * @author tommaso.severi2@studio.unibo.it
 */
public class FireBall extends PotionImpl {

    private static final String NAME = "fireball";
    private static final double ATTACK_RATE = 0.0;
    private static final double DMG = 30.0;
    private static final double RAD = 10.0;
    private static final double WAIT_TIME = 10.0;

    /**
     * Creates a new potion of type fireball 
     */
    public FireBall(final World parentWorld) {
        super(NAME, parentWorld, RAD, DMG, ATTACK_RATE, WAIT_TIME);
    }
}
