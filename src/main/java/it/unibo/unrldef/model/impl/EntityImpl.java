package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.World;

/**
 * An entity in a game with a position and a name.
 * 
 * @author danilo.maglia@studio.unibo.it
 * @author tommaso.ceredi@studio.unibo.it
 */
public abstract class EntityImpl implements Entity {
    private Optional<Position> position;
    private final String name;
    private World parentWorld;

    /**
     * Create a new entity with a name.
     * @param name the name of the entity
     */
    public EntityImpl(final String name) {
        this.position = Optional.empty();
        this.name = name;
    }

    /**
     * Set the parent world of the entity.
     * @param parentWorld the parent world to be set to the entity
     */
    @Override
    public void setParentWorld(final World parentWorld) {
        this.parentWorld = parentWorld;
    }

    /**
     * Get the position of the entity.
     * @return the position of the entity
     */
    @Override
    public Optional<Position> getPosition() {
        return this.position;
    }

    /**
     * Get the name of the entity.
     * @return the name of the entity
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Get the parent world of the entity.
     * @return the parent world of the entity
     */
    @Override
    public World getParentWorld() {
        return this.parentWorld;
    }

    /**
     * Set the position of the entity.
     * @param x the x position to be set to the entity
     * @param y the y position to be set to the entity
     */
    @Override
    public void setPosition(final double x, final double y) {
        this.position = Optional.of(new Position(x, y));
    }

    @Override
    public abstract void updateState(long time);
}
