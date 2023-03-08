package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.World;

/**
 * A Tower of archers that can attack enemies
 * @author tommaso.ceredi@studio.unibo.it
 */
public class Hunter extends TowerImpl {

    final private static int COST = 80;
    final private static double ATTACK_FOR_SECOND = 0.7;
    final private static int DAMAGE = 7;
    final private static String NAME = "sdrogo hunter";
    final private static double RADIOUS = 8;

    public Hunter(final Position hunterPosition, final World parentWorld) {
        super(hunterPosition, NAME, parentWorld, RADIOUS, DAMAGE, ATTACK_FOR_SECOND, COST);
    }
}
