package it.unibo.unrldef.model.impl;

import java.util.List;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Tower;

/**
 * A cannon that can attack enemies
 * @author tommaso.ceredi@studio.unibo.it
 */
public class Cannon extends TowerImpl {

    final private static int COST = 100;
    final private static long ATTACK_FOR_SECOND = 1000;
    final private static int DAMAGE = 10;
    final public static String NAME = "sdrogo cannon";
    final private static double RADIOUS = 5;
    final private static double EXPLOSION_RADIUS = 2;
    private Enemy target;
    
    public Cannon(final Position cannonPosition) {
        super(cannonPosition, NAME, RADIOUS, DAMAGE, ATTACK_FOR_SECOND, COST);
    }

    @Override
    public Tower copy() {
        return new Hunter(this.getPosition().get());
    }

    @Override
    protected void attack() {
        final List<Enemy> enemiesInRange = this.getParentWorld().sorroundingEnemies(this.getPosition().get(), this.getRadius());
        if (!enemiesInRange.contains(this.target)) {
            this.target = enemiesInRange.get(0);
        }
        if (!enemiesInRange.isEmpty()) {
            this.target.reduceHealth(this.getDamage());
            for (Enemy enemy : this.getParentWorld().sorroundingEnemies(this.target.getPosition().get(), EXPLOSION_RADIUS)) {
                enemy.reduceHealth(this.getDamage());
            }
        }
    }
}
