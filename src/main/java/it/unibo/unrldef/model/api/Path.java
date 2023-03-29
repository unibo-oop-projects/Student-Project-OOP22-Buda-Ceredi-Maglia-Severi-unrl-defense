package it.unibo.unrldef.model.api;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;

/**
 * Interface that represents a path in the game Unreal Defense.
 * 
 * @author danilo.maglia@studio.unibo.it
 */
public interface Path {

    /**
     * Enum that represents the direction of the path.
     */
    enum Direction {
        /**
         * UP direction.
         */
        UP,
        /**
         * DOWN direction.
         */
        DOWN,
        /**
         * LEFT direction.
         */
        LEFT,
        /**
         * RIGHT direction.
         */
        RIGHT,
        /**
         * END direction.
         */
        END
    }

    /**
     * Get the direction at the given index.
     * 
     * @param index index of the direction
     * @return the direction at the given index
     */
    Pair<Direction, Double> getDirection(int index);

    /**
     * Add a direction to the path.
     * 
     * @param direction direction where the entity will move
     * @param units     amount of space the entity will move in the given direction
     */
    void addDirection(Direction direction, double units);

    /**
     * 
     * @return the spawning point of the entities.
     */
    Position getSpawningPoint();
}
