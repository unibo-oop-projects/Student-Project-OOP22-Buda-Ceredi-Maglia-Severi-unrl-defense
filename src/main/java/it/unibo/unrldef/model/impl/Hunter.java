package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Tower;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.World;

/**
 * A Tower of archers that can attack enemies
 * @author tommaso.ceredi@studio.unibo.it
 */
public class Hunter extends DefenseEntity implements Tower {

    final private static int COST = 80;
    final private static double ATTACK_FOR_SECOND = 0.7;
    final private static int DAMAGE = 7;
    final private static String NAME = "sdrogo hunter";
    final private static double RADIOUS = 8;

    public Hunter(final Optional<Position> position) {
        super(position, NAME, RADIOUS, DAMAGE, ATTACK_FOR_SECOND);
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    protected void attack() {
        this.getTargetedEnemies().get(0).reduceHealth(this.getDamage());
        if (this.getTargetedEnemies().length >= 1) {
            this.getTargetedEnemies().get(1).reduceHealth(this.getDamage());
        }
    }

    @Override
    public void updateState() {
        this.updateTimer();
    }
    
}
