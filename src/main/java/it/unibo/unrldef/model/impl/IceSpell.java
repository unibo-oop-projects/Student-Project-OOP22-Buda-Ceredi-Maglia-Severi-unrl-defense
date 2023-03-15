package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.model.api.World;

/**
 * An ice spell used in a tower defense game
 * @author tommaso.severi2@studio.unibo.it
 */
public class IceSpell extends SpellImpl {
    
    public static final String NAME = "ice-spell";
    private static final long ATTACK_RATE = 5 * 100;
    private static final double DMG = 1.0;
    private static final double RAD = 200.0;
    private static final long WAIT_TIME = 5 * 1000;
    private static final int LINGERING_DAMAGE = 10;

    /**
     * Creates a new spell of type ice
     * @param parentWorld the world where it'll be having effect
     */
    public IceSpell(final World parentWorld) {
        super(NAME, parentWorld, RAD, DMG, ATTACK_RATE, WAIT_TIME, LINGERING_DAMAGE);
    }
}
