package it.unibo.unrldef.model.impl;

import java.util.List;

import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Tower;

/**
 * A cannon that can attack enemies.
 * 
 * @author tommaso.ceredi@studio.unibo.it
 */
public final class Cannon extends TowerImpl {

    private static final int COST = 200;
    private static final long ATTACK_FOR_SECOND = 2000;
    private static final int DAMAGE = 10;
    /**
     * Name of the tower.
     */
    public static final String NAME = "cannon";
    /**
     * Radious of the tower.
     */
    public static final double RADIOUS = 20;
    private static final double EXPLOSION_RADIUS = 5;

    /**
     * Constructor of Cannon.
     */
    public Cannon() {
        super(NAME, RADIOUS, DAMAGE, ATTACK_FOR_SECOND, COST);
    }

    @Override
    public Tower copy() {
        return new Cannon();
    }

    @Override
    protected void additionAttack(final Enemy target) {
        final List<Enemy> enemiesInExplosionRange = this.getParentWorld().sorroundingEnemies(target.getPosition().get(),
                EXPLOSION_RADIUS);
        if (!enemiesInExplosionRange.isEmpty()) {
            for (Enemy enemy : enemiesInExplosionRange) {
                enemy.reduceHealth(this.getDamage());
            }
        }
    }
}
