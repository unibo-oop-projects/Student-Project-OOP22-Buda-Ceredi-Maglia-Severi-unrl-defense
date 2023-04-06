package it.unibo.unrldef.model.impl;

import java.util.HashSet;
import java.util.Set;

import it.unibo.unrldef.model.api.Enemy;

/**
 * An ice spell used in a tower defense game to slow down enemies.
 * 
 * @author tommaso.severi2@studio.unibo.it
 */
public final class SnowStorm extends SpellImpl {
    /**
     * The name of the spell unique to the object type.
     */
    public static final String NAME = "snow_storm";
    /**
     * The radius of the spell unique to the object type.
     */
    public static final double RAD = 7.0;
    private static final long RECHARGE_TIME = 6 * 1000;
    private static final double DMG = 0.0;
    private static final long LINGERING_EFFECT_TIME = 6 * 1000;
    private static final long LINGERING_EFFECT_FREQ = 500;

    private static final double SPEED_REDUCTION = 0.2;
    private final Set<Enemy> enemiesEffected = new HashSet<>();

    /**
     * Creates a new spell of type ice.
     */
    public SnowStorm() {
        super(NAME, RAD, DMG, RECHARGE_TIME, LINGERING_EFFECT_TIME, LINGERING_EFFECT_FREQ);
    }

    @Override
    protected void effect(final Enemy target) {
        target.setSpeed(target.getSpeed() - (target.getSpeed() * SPEED_REDUCTION));
        this.enemiesEffected.add(target);
    }

    @Override
    protected void resetEffect() {
        this.enemiesEffected.forEach(e -> e.resetSpeed());
        this.enemiesEffected.clear();
    }
}
