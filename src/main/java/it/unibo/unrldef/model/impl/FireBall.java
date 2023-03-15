package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.model.api.World;

/**
 * A fireball spell used in a tower defense game
 * @author tommaso.severi2@studio.unibo.it
 */
public class FireBall extends SpellImpl {

    public static final String NAME = "fireball";
    private static final long ATTACK_RATE = 1 * 1000;
    private static final double DMG = 3.0;
    private static final double RAD = 100.0;
    private static final long WAIT_TIME = 8 * 1000;
    private static final int LINGERING_DAMAGE = 3;

    /**
     * Creates a new spell of type fireball
     * @param parentWorld the world where it'll be having effect
     */
    public FireBall(final World parentWorld) {
        super(NAME, parentWorld, RAD, DMG, ATTACK_RATE, WAIT_TIME, LINGERING_DAMAGE);
    }
}
