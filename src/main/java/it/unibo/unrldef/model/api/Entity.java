package it.unibo.unrldef.model.api;

import java.util.Optional;

import it.unibo.unrldef.common.Position;

/**
 * @author danilo.maglia@studio.unibo.it
 */
public interface Entity {
    /**
     * 
     * @return the position of the entity
     */
    Optional<Position> getPosition();

    /**
     * 
     * @return the name of the entity
     */
    String getName();

    /**
     * Set the parent world of the entity.
     * @param world the parent world to be set to the entity
     */
    void setParentWorld(World world);

    /**
     * Get the parent world of the entity.
     * @return the parent world of the entity
     */
    World getParentWorld();

    /**
     * Set the position of the entity.
     * @param x the x position to be set to the entity
     * @param y the y position to be set to the entity
     */
    void setPosition(double x, double y);

    /**
     * Update the state of the object.
     * 
     * @param time the amount of time to add to the internal timer
     */
    void updateState(long time);
}
