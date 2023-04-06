package it.unibo.unrldef.model.api;

import it.unibo.unrldef.common.Position;

/**
 * A spell that can be used by a player in a strategic game.
 * 
 * @author tommaso.severi2@studio.unibo.it
 */
public interface Spell extends Entity {
    /**
     * Tries to set the spell in its activated state, dealing damage and its effect.
     * 
     * @param position the desired place to throw th spell at
     * @return true if the spell is ready to be used, false otherwise
     */
    boolean ifPossibleActivate(Position position);

    /**
     * @return the damage dealt by the spell while it's active
     */
    double getDamage();

    /**
     * @return the radius of the spell
     */
    double getRadius();

    /**
     * @return true if the spell is being used, false otherwise
     */
    boolean isActive();

    /**
     * @return true if the spell is ready to be used, false otherwise
     */
    public boolean isReady();
}
