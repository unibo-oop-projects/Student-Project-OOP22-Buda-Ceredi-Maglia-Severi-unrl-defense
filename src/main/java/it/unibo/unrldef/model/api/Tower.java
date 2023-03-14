package it.unibo.unrldef.model.api;

import java.util.Optional;

/**
 * A tower that can be placed in a world
 * @author tommaso.ceredi@studio.unibo.it
 */
public interface Tower extends Entity {

    /**
     * @return the cost of the tower
     */
    public int getCost();

    /**
     * @return a copy of the tower
     */
    public Tower copy();

    /**
     * @param world the world where the tower is placed
     */
    public void setParentWorld(final World world);

    /**
     * @return the Enemy that the tower is attacking
     */
    public abstract Optional<Enemy> getTarget();
    
}
