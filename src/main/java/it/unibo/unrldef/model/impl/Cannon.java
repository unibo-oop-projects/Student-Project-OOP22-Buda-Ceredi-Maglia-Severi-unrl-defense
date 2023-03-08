package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.World;

/**
 * A cannon that can attack enemies
 * @author tommaso.ceredi@studio.unibo.it
 */
public class Cannon extends TowerImpl {

    final private static int COST = 100;
    final private static double ATTACK_FOR_SECOND = 1.5;
    final private static int DAMAGE = 10;
    final private static String NAME = "sdrogo cannon";
    final private static double RADIOUS = 5;
    
    public Cannon(final Position cannonPosition, final World parentWorld) {
        super(cannonPosition, NAME, parentWorld, RADIOUS, DAMAGE, ATTACK_FOR_SECOND, COST);
    }
}
