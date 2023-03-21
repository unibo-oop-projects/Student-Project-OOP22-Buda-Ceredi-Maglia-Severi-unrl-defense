package it.unibo.unrldef.model.impl;

import java.util.HashSet;
import java.util.Set;

import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.World;

/**
 * An ice spell used in a tower defense game
 * @author tommaso.severi2@studio.unibo.it
 */
public class SnowStorm extends SpellImpl {
    
    public static final String NAME = "snow_storm";
    private static final long ATTACK_RATE = 6 * 1000;
    private static final double DMG = 10.0;
    private static final double RAD = 200.0;
    private static final long LINGERING_EFFECT_TIME = 4 * 1000;
    private static final long LINGERING_EFFECT_FREQ = 500;

    private final double speedReduction = 3.0;
    private final Set<Enemy> enemiesEffected = new HashSet<>();

    /**
     * Creates a new spell of type ice
     * @param parentWorld the world where it'll be having effect
     */
    public SnowStorm(final World parentWorld) {
        super(NAME, parentWorld, RAD, DMG, ATTACK_RATE, LINGERING_EFFECT_TIME, LINGERING_EFFECT_FREQ);
    }

    @Override
    protected void effect(final Enemy enemy) {
        enemy.setSpeed(enemy.getSpeed()-this.speedReduction);
        this.enemiesEffected.add(enemy);
    }

    @Override
    protected void resetEffect() {
        this.enemiesEffected.forEach(e -> e.resetSpeed());
        this.enemiesEffected.clear();
    }
}
