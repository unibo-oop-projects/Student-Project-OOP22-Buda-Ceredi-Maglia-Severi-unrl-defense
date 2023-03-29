package it.unibo.unrldef.model.impl;

import java.util.List;
import java.util.Optional;

import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Tower;

/**
 * 
 * A Tower of archers that can attack enemies.
 * 
 * @author tommaso.ceredi@studio.unibo.it
 */
public final class Hunter extends TowerImpl {

    private static final int COST = 80;
    private static final long ATTACK_FOR_SECOND = 1000;
    private static final int DAMAGE = 50;
    /**
     * The name of the tower.
     */
    public static final String NAME = "sdrogo hunter";
    /**
     * The radius of the tower.
     */
    public static final double RADIOUS = 30;
    private Optional<Enemy> target = Optional.empty();

    /**
     * Constructor of the Hunter.
     * 
     * @param hunterPosition
     */
    public Hunter(final Position hunterPosition) {
        super(hunterPosition, NAME, RADIOUS, DAMAGE, ATTACK_FOR_SECOND, COST);
    }

    @Override
    public Tower copy() {
        return new Hunter();
    }

    @Override
    protected void attack() {
        final List<Enemy> enemiesInRange = this.getParentWorld().sorroundingEnemies(this.getPosition().get(),
            this.getRadius());
        if (!enemiesInRange.isEmpty()) {
            this.target = enemiesInRange.stream().findFirst();
            this.target.ifPresent(enemy -> enemy.reduceHealth(this.getDamage()));
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
