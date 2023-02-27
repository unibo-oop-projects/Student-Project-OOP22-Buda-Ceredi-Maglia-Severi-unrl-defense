package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.DefenseEntity;
import it.unibo.unrldef.model.api.Tower;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.World;

/**
 * @author tommaso.ceredi@studio.unibo.it
 */
public class Hunter extends DefenseEntity implements Tower {

    final private static int COST = 80;
    final private static int ATTACK_FOR_SECOND = 3;
    final private static int DAMAGE = 7;
    final private static String NAME = "hunter";
    final private static double RADIOUS = 8;

    final private double timeElapsed = 0;

    public Hunter(Optional<Position> position) {
        super(position, NAME, RADIOUS, DAMAGE);
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
