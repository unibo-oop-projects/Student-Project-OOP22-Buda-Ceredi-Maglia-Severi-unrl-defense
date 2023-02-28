package it.unibo.unrldef.model.api;

/**
 * An enemy in a strategic game
 * @author danilo.maglia@studio.unibo.it
 */
public interface Enemy {
    /**
     * @return the health of the enemy
     */
    public int getHealth();

    /**
     * @return the speed of the enemy
     */
    public int getSpeed();

    /**
     * Method that reduces the health of the enemy
     * @param amount the amount of health to reduce
     */
    public void reduceHealth(final int amount);

    /**
     * @return true if the enemy is dead, false otherwise
     */
    public boolean isDead();

    /**
     * 
     * @return true if the enemy has reached the end of the path, false otherwise
     */
    public boolean hasReachedEndOfPath();

    /**
     * @return a copy of the enemy
     */
    public Enemy copy();
}
