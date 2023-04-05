package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.model.api.Enemy;

/**
 * A fireball spell used in a tower defense game.
 * @author tommaso.severi2@studio.unibo.it
 */
public final class FireBall extends SpellImpl {

    /**
     * The name of the spell unique to the object type.
     */
    public static final String NAME = "fireball";
    /**
     * The radius of the spell unique to the object type.
     */
    public static final double RAD = 6.0;
    private static final long RECHARGE_TIME = 8 * 1000;
    private static final double DMG = 20.0;
    private static final long LINGERING_EFFECT_TIME = 5 * 1000;
    private static final long LINGERING_EFFECT_FREQ = 1 * 1000;

    private static final double LINGERING_DAMAGE = 4.0;

    /**
     * Creates a new spell of type fireball.
     */
    public FireBall() {
        super(NAME, RAD, DMG, RECHARGE_TIME, LINGERING_EFFECT_TIME, LINGERING_EFFECT_FREQ);
    }

    @Override
    protected void effect(final Enemy enemy) {
        enemy.reduceHealth(LINGERING_DAMAGE);
    }

    @Override
    protected void resetEffect() { }
}
