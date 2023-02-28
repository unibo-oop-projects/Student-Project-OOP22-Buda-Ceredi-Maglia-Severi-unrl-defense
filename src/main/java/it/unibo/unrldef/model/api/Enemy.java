package it.unibo.unrldef.model.api;

/**
 * An enemy in a strategic game
 * @author danilo.maglia@studio.unibo.it
 */
public interface Enemy {
    /**
     * @return the health of the enemy
     */
    public double getHealth();

    /**
     * @return the speed of the enemy
     */
    public double getSpeed();

    /**
     * Method that reduces the health of the enemy
     * @param amount the amount of health to reduce
     */
    public void reduceHealth(final double amount);

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
     * Method that moves the enemy following the path
     * 
     */
    public void move(long time);

    /**
     * @return a copy of the enemy
     */
    public Enemy copy();
}
