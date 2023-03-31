package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Tower;

/**
 * A Tower of archers that can attack enemies
 * 
 * @author tommaso.ceredi@studio.unibo.it
 */
public class Hunter extends TowerImpl {

    final private static int COST = 100;
    final private static long ATTACK_FOR_SECOND = 750;
    final private static int DAMAGE = 5;
    final public static String NAME = "hunter";
    final public static double RADIOUS = 15;

    public Hunter() {
        super(NAME, RADIOUS, DAMAGE, ATTACK_FOR_SECOND, COST);
    }

    @Override
    public Tower copy() {
        return new Hunter();
    }

    @Override
    protected void additionAttack(final Enemy enemy) {}
}
