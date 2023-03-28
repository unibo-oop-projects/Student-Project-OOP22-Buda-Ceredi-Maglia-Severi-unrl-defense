package it.unibo.unrldef.model.impl;

import java.util.HashSet;
import java.util.Set;

import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.World;

/**
 * An ice spell used in a tower defense game to slow down enemies.
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
    private static final long ATTACK_RATE = 6 * 1000;
    private static final double DMG = 10.0;
    private static final long LINGERING_EFFECT_TIME = 4 * 1000;
    private static final long LINGERING_EFFECT_FREQ = 500;

    private final double speedReduction = 0.1;
    private final Set<Enemy> enemiesEffected = new HashSet<>();

    /**
     * Creates a new spell of type ice.
     * @param parentWorld the world where it'll be having effect
     */
    public SnowStorm(final World parentWorld) {
        super(NAME, parentWorld, RAD, DMG, ATTACK_RATE, LINGERING_EFFECT_TIME, LINGERING_EFFECT_FREQ);
    }

    @Override
    protected void effect(final Enemy enemy) {
        enemy.setSpeed(enemy.getSpeed() - (enemy.getSpeed() * this.speedReduction));
        this.enemiesEffected.add(enemy);
    }

    @Override
    protected void resetEffect() {
        this.enemiesEffected.forEach(e -> e.resetSpeed());
        this.enemiesEffected.clear();
    }
}
