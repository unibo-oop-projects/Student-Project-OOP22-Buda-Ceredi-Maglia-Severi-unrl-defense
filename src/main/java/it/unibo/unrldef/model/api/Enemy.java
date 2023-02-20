package it.unibo.unrldef.model.api;

/**
 * An enemy in a strategic game
 * @author danilo.maglia@studio.unibo.it
 */
public interface Enemy {
    /**
     * 
     * @return the health of the enemy
     */
    public int getHealth();

    /**
     * Method that reduces the health of the enemy
     * @param amount the amount of health to reduce
     */
    public void reduceHealth(int amount);

    /**
     * @return true if the enemy is dead, false otherwise
     */
    public boolean isDead();

    
}
