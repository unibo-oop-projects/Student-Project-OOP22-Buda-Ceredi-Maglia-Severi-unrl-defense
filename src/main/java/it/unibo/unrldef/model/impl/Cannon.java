package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Tower;

/**
 * A cannon that can attack enemies
 * @author tommaso.ceredi@studio.unibo.it
 */
public class Cannon extends DefenseEntity implements Tower {

    final private static int COST = 100;
    final private static double ATTACK_FOR_SECOND = 1.5;
    final private static int DAMAGE = 10;
    final private static String NAME = "sdrogo cannon";
    final private static double RADIOUS = 5;

    public Cannon(final Optional<Position> cannonPosition) {
        super(cannonPosition, NAME, RADIOUS, DAMAGE, ATTACK_FOR_SECOND);
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    protected void attack() {
        this.getTargetedEnemies().get(0).reduceHealth(this.getDamage());
    }

    @Override
    public void updateState() {
        this.updateTimer();
    }

    
}
