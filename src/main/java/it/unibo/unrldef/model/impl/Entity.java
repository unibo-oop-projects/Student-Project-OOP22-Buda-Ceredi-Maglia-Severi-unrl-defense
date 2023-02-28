package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.World;

/**
 * An entity in a game with a position and a name
 * @author danilo.maglia@studio.unibo.it
 * @author tommaso.ceredi@studio.unibo.it
 */
public abstract class Entity {
    private Optional<Position> position; 
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
     * Update the state of the object
     * @param time the amount of time to add to the internal timer
     */
    public abstract void updateState(long time);
}
