package it.unibo.unrldef.model.api;

import it.unibo.unrldef.common.Position;

/**
 * A spell that can be trown by a player in a strategic game.
 * @author tommaso.severi2@studio.unibo.it
 */
public interface Spell extends Entity {
    /**
     * Tries to set the spell in its activation state, dealing damage.
     * @param position the desired place to throw th spell at
     * @return true if the spell is ready to be used, false otherwise
     */
    boolean ifPossibleActivate(Position position);

    /**
     * @return the damage dealt by the spell every frame it's active
     */
    double getDamage();

    /**
     * @return the radius of the spell effect
     */
    double getRadius();

    /**
     * @return true if the spell is being used, false otherwise
     */
    boolean isActive();
}
