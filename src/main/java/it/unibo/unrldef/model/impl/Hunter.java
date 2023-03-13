package it.unibo.unrldef.model.impl;

import java.util.List;
import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Tower;

/**
 * A Tower of archers that can attack enemies
 * @author tommaso.ceredi@studio.unibo.it
 */
public class Hunter extends TowerImpl {

    final private static int COST = 80;
    final private static long ATTACK_FOR_SECOND = 1000;
    final private static int DAMAGE = 7;
    final public static String NAME = "sdrogo hunter";
    final private static double RADIOUS = 8;
    private Enemy target;

    public Hunter(final Position hunterPosition) {
        super(hunterPosition, NAME, RADIOUS, DAMAGE, ATTACK_FOR_SECOND, COST);
    }

    @Override
    public Tower copy() {
        return new Hunter(null);
    }

    @Override
    protected void attack() {
        final List<Enemy> enemiesInRange = this.getParentWorld().sorroundingEnemies(this.getPosition().get(), this.getRadius());
        if (!enemiesInRange.contains(this.target)) {
            this.target = enemiesInRange.get(0);
        }
        if (!enemiesInRange.isEmpty()) {
            this.target.reduceHealth(this.getDamage());
        }
    }

    public Optional<Enemy> getTarget() {
        return this.isAttacking() ? Optional.of(this.target) : Optional.empty();
    }
}
