package it.unibo.unrldef.model.impl;

import java.util.List;

import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Tower;

/**
 * A cannon that can attack enemies
 * 
 * @author tommaso.ceredi@studio.unibo.it
 */
public class Cannon extends TowerImpl {

    final private static int COST = 200;
    final private static long ATTACK_FOR_SECOND = 2000;
    final private static int DAMAGE = 10;
    final public static String NAME = "cannon";
    final public static double RADIOUS = 20;
    final private static double EXPLOSION_RADIUS = 5;

    public Cannon() {
        super(NAME, RADIOUS, DAMAGE, ATTACK_FOR_SECOND, COST);
    }

    @Override
    public Tower copy() {
        return new Cannon();
    }

    @Override
    protected void additionAttack(Enemy target) {
        final List<Enemy> enemiesInExplosionRange = this.getParentWorld().sorroundingEnemies(target.getPosition().get(),
                EXPLOSION_RADIUS);
        if (!enemiesInExplosionRange.isEmpty()) {
            for (Enemy enemy : enemiesInExplosionRange) {
                enemy.reduceHealth(this.getDamage());
            }
        }
    }
}
