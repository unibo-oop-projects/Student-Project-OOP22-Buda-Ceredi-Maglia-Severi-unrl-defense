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
    private final String name;

    /**
     * @param position the position to be set to the entity
     * @param name the name of the entity
     */
    public Entity(Optional<Position> position, String name) {
        this.setPosition(position);
        this.name = name;
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

    /**
     * 
     * @param position the position to be set to the entity
     */
    public void setPosition(Optional<Position> position) {
        this.position = position;
    }

    /**
     * Update thr state of the object
     */
    public abstract void updateState();
}
