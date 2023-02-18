package it.unibo.unrldef.model.api;

import java.util.Optional;

/**
 * A potion that can be trown by a player in a strategic game
 * @author tommaso.severi2@studio.unibo.it
 */
public interface Potion {
    
    /**
     * @return the name of the potion
     */
    public String getName();

    /**
     * @return the damage dealt by the potion every frame it's active
     */
    public Double getDamagePerFrame();

    /**
     * @return the radius of the potion effect
     */
    public Integer getRadius();

    /**
     * @return the current state the potion is currently at when recharging
     */
    public Double getRechargeState();

    /**
     * @return the position where the potion was thrown but it could be null in case it's recharging
     */
    public Optional<Position> getCurrentPosition();

    /**
     * @return true if the potion is ready to be used, false otherwise
     */
    public boolean isReady();

    /**
     * Informs the potion of the passage of time
     */
    public void updateState();
}
