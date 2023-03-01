package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.World;

/**
 * An entity in a game with a position and a name
 * @author danilo.maglia@studio.unibo.it
 * @author tommaso.ceredi@studio.unibo.it
 */
public abstract class EntityImpl implements Entity  {
    private Optional<Position> position; 
    private final String name;
    private final World parentWorld;
    

    /**
     * @param position the position to be set to the entity
     * @param name the name of the entity
     * @param parentWorld the parent world of the entity
     */
    public EntityImpl(final Optional<Position> position, final String name, final World parentWorld) {
        this.position = position;
        this.name = name;
        this.parentWorld = parentWorld;
    }


    public Optional<Position> getPosition() {
        return this.position;
    }


    public String getName() {
        return this.name;
    }

    public World getParentWorld() {
        return this.parentWorld;
    }


    public void setPosition(double x, double y) {
        this.position = Optional.of(new Position(x,y));
    }


    public abstract void updateState(long time);
}
