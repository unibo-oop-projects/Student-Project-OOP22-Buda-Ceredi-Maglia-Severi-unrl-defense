package it.unibo.unrldef.model.api;

/**
 * the integrity of a game entity.
 * 
 * @author francesco.buda3@studio.unibo.it
 */
public interface Integrity {
    /**
     * 
     * @return the entity's remaining hearts
     */
    int getHearts();

    /**
     * damage the entity.
     * 
     * @param hearts the hearts to subtract from the entity's remaining hearts
     * 
     */
    void damage(int hearts);

    /**
     * 
     * @return true if the entity'integrity is compromised, false otherwise
     */
    Boolean isCompromised();
}
