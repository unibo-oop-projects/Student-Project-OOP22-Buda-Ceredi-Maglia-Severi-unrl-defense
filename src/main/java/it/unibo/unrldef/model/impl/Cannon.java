package it.unibo.unrldef.model.impl;

import java.util.List;
import java.util.Optional;

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
    final private static double EXPLOSION_RADIUS = 4;
    private Optional<Enemy> target = Optional.empty();
    
    public Cannon() {
        super(NAME, RADIOUS, DAMAGE, ATTACK_FOR_SECOND, COST);
    }

    @Override
    public Tower copy() {
        return new Cannon();
    }

    @Override
    protected void attack() {
        final List<Enemy> enemiesInRange = this.getParentWorld().sorroundingEnemies(this.getPosition().get(), this.getRadius());
        if (!enemiesInRange.isEmpty()) {
            if (this.target.isEmpty() || !enemiesInRange.contains(this.target.get())) {
                this.target = Optional.of(enemiesInRange.get(0));
            }
            this.target.get().reduceHealth(this.getDamage());
            // explosion
            final List<Enemy> enemiesInExplosionRange = this.getParentWorld().sorroundingEnemies(this.target.get().getPosition().get(), EXPLOSION_RADIUS);
            if (!enemiesInExplosionRange.isEmpty()) {
                for (Enemy enemy : enemiesInExplosionRange) {
                    enemy.reduceHealth(this.getDamage());
                }
            }
        } else {
            this.target = Optional.empty();
        }
    }

    @Override
    public Optional<Enemy> getTarget() {
        if (this.target != null) {
            return this.isAttacking() ? this.target : Optional.empty();
        }
        return Optional.empty();
    }
}
