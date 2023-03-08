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
    public Optional<Position> getPosition();
    /**
     * 
     * @return the name of the entity
     */
    public String getName();
    /**
     * 
     * @param x the x position to be set to the entity
     * @param y the y position to be set to the entity
     */
    public void setPosition(double x, double y);
    /**
     * Update the state of the object
     * @param time the amount of time to add to the internal timer
     */
    public void updateState(long time);
}
