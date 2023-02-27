package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.DefenseEntity;
import it.unibo.unrldef.model.api.Tower;

/**
 * @author tommaso.ceredi@studio.unibo.it
 */
public class Cannon extends DefenseEntity implements Tower {

    final private static int COST = 100;
    final private static int ATTACK_FOR_SECOND = 2;
    final private static int DAMAGE = 10;
    final private static String NAME = "cannon";
    final private static double RADIOUS = 5;

    public Cannon(Optional<Position> cannonPosition) {
        super(cannonPosition, NAME, RADIOUS, DAMAGE);
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    public int getAttackSpeed() {
        return ATTACK_FOR_SECOND;
    }
}
