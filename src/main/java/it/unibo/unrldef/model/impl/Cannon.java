package it.unibo.unrldef.model.impl;

import java.util.List;
import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Tower;

/**
 * 
 * A cannon that can attack enemies.
 * 
 * @author tommaso.ceredi@studio.unibo.it
 */
public final class Cannon extends TowerImpl {

    private static final int COST = 100;
    private static final long ATTACK_FOR_SECOND = 1000;
    private static final int DAMAGE = 5;
    /**
     * The name of the tower.
     */
    public static final String NAME = "sdrogo cannon";
    /**
     * The radius of the tower.
     */
    public static final double RADIOUS = 20;
    private static final double EXPLOSION_RADIUS = 4;
    private Optional<Enemy> target = Optional.empty();

    /**
     * Constructor of the Cannon.
     * 
     * @param cannonPosition
     */
    public Cannon(final Position cannonPosition) {
        super(cannonPosition, NAME, RADIOUS, DAMAGE, ATTACK_FOR_SECOND, COST);
    }

    @Override
    public Tower copy() {
        return new Cannon(null);
    }

    @Override
    protected void attack() {
        final List<Enemy> enemiesInRange = this.getParentWorld().sorroundingEnemies(this.getPosition().get(),
                this.getRadius());
        if (!enemiesInRange.isEmpty()) {
            this.target = enemiesInRange.stream().findAny();
            this.target.ifPresent(enemy -> enemy.reduceHealth(this.getDamage()));
            final List<Enemy> enemiesInExplosionRange = this.getParentWorld().sorroundingEnemies(
                    this.getPosition().get(),
                    EXPLOSION_RADIUS);
            enemiesInExplosionRange.stream().forEach(enemy -> enemy.reduceHealth(this.getDamage()));
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
