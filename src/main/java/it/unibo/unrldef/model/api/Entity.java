package it.unibo.unrldef.model.api;

import java.util.Optional;

import it.unibo.unrldef.common.Position;

/**
 * An entity in a game with a position and a name
 * @author danilo.maglia@studio.unibo.it
 * @author tommaso.ceredi@studio.unibo.it
 */
public abstract class Entity {
    private Optional<Position> position; 
    private long timeSinceLastAction;
    private final String name;
    private final World parentWorld;
    

    /**
     * @param position the position to be set to the entity
     * @param name the name of the entity
     */
    public Entity(final Optional<Position> position, final String name, final World parentWorld) {
        this.setPosition(position);
        this.name = name;
        this.parentWorld = parentWorld;
        this.timeSinceLastAction = 0;
    }

    /**
     * 
     * @return the position of the entity
     */
    public Optional<Position> getPosition() {
        return this.position;
    }

    /**
     * 
     * @return the name of the entity
     */
    public String getName() {
        return this.name;
    }

    public World getParentWorld() {
        return this.parentWorld;
    }

    /**
     * 
     * @param position the position to be set to the entity
     */
    public void setPosition(Optional<Position> position) {
        this.position = position;
    }

    /**
     * 
     * @return the time elapsed since the last action of the entity was performed in milliseconds
     */
    public long getTimeSinceLastAction() {
        return this.timeSinceLastAction;
    }

    /**
     * 
     * @param amount increase the time elapsed in milliseconds since the last action
     */
    public void incrementTime(long amount) {
        this.timeSinceLastAction += amount;
    }

    /**
     * Reset elapsed time
     */
    public void resetElapsedTime() {
        this.timeSinceLastAction = 0;
    }

    /**
     * Update the state of the object
     */
    public abstract void updateState();
}
