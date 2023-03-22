package it.unibo.unrldef.model.impl;

import java.util.List;
import java.util.Optional;

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
    final private static int DAMAGE = 5;
    final public static String NAME = "sdrogo cannon";
    final public static double RADIOUS = 20;
    final private static double EXPLOSION_RADIUS = 2;
    private Enemy target;
    
    public Cannon(final Position cannonPosition) {
        super(cannonPosition, NAME, RADIOUS, DAMAGE, ATTACK_FOR_SECOND, COST);
    }

    @Override
    public Tower copy() {
        return new Cannon(null);
    }

    @Override
    protected void attack() {
        final List<Enemy> enemiesInRange = this.getParentWorld().sorroundingEnemies(this.getPosition().get(), this.getRadius());
        if (!enemiesInRange.isEmpty()) {
            if (!enemiesInRange.contains(this.target)) {
                this.target = enemiesInRange.get(0);
            }
            this.target.reduceHealth(this.getDamage());
            // explosion
            final List<Enemy> enemiesInExplosionRange = this.getParentWorld().sorroundingEnemies(this.getPosition().get(), EXPLOSION_RADIUS);
            if (!enemiesInExplosionRange.isEmpty()) {
                for (Enemy enemy : enemiesInExplosionRange) {
                    enemy.reduceHealth(this.getDamage());
                    System.out.println("CANNON: " + enemy.getHealth());
                }
            }
        }
    }

    @Override
    public Optional<Enemy> getTarget() {
        //System.out.println("CANNON target: " + this.target);
        return this.isAttacking() ? Optional.ofNullable(this.target) : Optional.empty();
    }
}
