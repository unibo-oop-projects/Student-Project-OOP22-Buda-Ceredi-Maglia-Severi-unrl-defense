package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Tower;

/**
 * A Tower of archers that can attack enemies.
 * 
 * @author tommaso.ceredi@studio.unibo.it
 */
public final class Hunter extends TowerImpl {

    private static final int COST = 100;
    private static final long ATTACK_FOR_SECOND = 750;
    private static final int DAMAGE = 5;
    /**
     * Name of the tower.
     */
    public static final String NAME = "hunter";
    /**
     * Radious of the tower.
     */
    public static final double RADIOUS = 15;

    /**
     * Constructor of Hunter.
     */
    public Hunter() {
        super(NAME, RADIOUS, DAMAGE, ATTACK_FOR_SECOND, COST);
    }

    @Override
    public Tower copy() {
        return new Hunter();
    }

    @Override
    protected void additionAttack(final Enemy enemy) {
    }
}
