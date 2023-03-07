package it.unibo.unrldef.model.api;

import java.util.Optional;

import it.unibo.unrldef.common.Position;

/**
 * A potion that can be trown by a player in a strategic game
 * @author tommaso.severi2@studio.unibo.it
 */
public interface Spell {
    
    /**
     * @return the name of the potion
     */
    public String getName();

    /**
     * Sets the potion in its activation state dealing damage
     * @param position the desired place to throw th potion at
     * @return true if the potion is ready to be used, false otherwise
     */
    public boolean tryActivation(final Position position);

    /**
     * @return the damage dealt by the potion every frame it's active
     */
    public double getDamage();

    /**
     * @return the radius of the potion effect
     */
    public double getRadius();

    /**
     * @return the position where the potion was thrown but it could be null in case it's recharging
     */
    public Optional<Position> getPosition();

    /**
     * @return true if the potion is being used, false otherwise
     */
    public boolean isActive();
}
