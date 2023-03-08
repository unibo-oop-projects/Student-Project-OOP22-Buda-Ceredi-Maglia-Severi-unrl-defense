package it.unibo.unrldef.model.api;

/**
 * A tower that can be placed in a world
 * @author tommaso.ceredi@studio.unibo.it
 */
public interface Tower extends Entity {

    /**
     * @return the cost of the tower
     */
    public int getCost();
    
}
