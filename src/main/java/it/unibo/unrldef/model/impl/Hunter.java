package it.unibo.unrldef.model.impl;

import java.util.List;
import java.util.Optional;

import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Tower;

/**
 * A Tower of archers that can attack enemies
 * @author tommaso.ceredi@studio.unibo.it
 */
public class Hunter extends TowerImpl {

    final private static int COST = 100;
    final private static long ATTACK_FOR_SECOND = 750;
    final private static int DAMAGE = 5;
    final public static String NAME = "hunter";
    final public static double RADIOUS = 15;
    private Optional<Enemy> target = Optional.empty();

    public Hunter() {
        super(NAME, RADIOUS, DAMAGE, ATTACK_FOR_SECOND, COST);
    }

    @Override
    public Tower copy() {
        return new Hunter();
    }

    @Override
    protected void attack() {
        final List<Enemy> enemiesInRange = this.getParentWorld().sorroundingEnemies(this.getPosition().get(), this.getRadius());
        if (!enemiesInRange.isEmpty()) {
            if (this.target.isEmpty() || !enemiesInRange.contains(this.target.get())) {
                this.target = Optional.of(enemiesInRange.get(0));
            }
            this.target.get().reduceHealth(this.getDamage());
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
