package it.unibo.unrldef.model.api;

import java.util.Objects;
import java.util.Optional;

import it.unibo.unrldef.common.Position;

/**
 * An entity in a game with a position and a name
 * @author danilo.maglia@studio.unibo.it
 */
public abstract class Entity {
    private Optional<Position> position; 
    private final String name;

    /**
     * @param position the position to be set to the entity
     * @param name the name of the entity
     */
    public Entity(Position position, String name) {
        this.setPosition(position);
        this.name = Objects.requireNonNull(name);
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
    public void setPosition(Position position) {
        this.position = Optional.of(position);
    }
}
