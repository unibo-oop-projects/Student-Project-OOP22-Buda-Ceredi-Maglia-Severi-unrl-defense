package it.unibo.unrldef.model.api;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;

public interface Path {

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        END
    }

    /**
     * 
     * @return the direction at the given index
     */
    public Pair<Direction, Double> getDirection(final int index);

    /**
     * Add a direction to the path
     * @param direction direction where the entity will move
     * @param units amount of space the entity will move in the given direction
     */
    public void addDirection(final Direction direction, final double units);

    /**
     * 
     * @return the spawning point of the entities
     */
    public Position getSpawningPoint();
}
