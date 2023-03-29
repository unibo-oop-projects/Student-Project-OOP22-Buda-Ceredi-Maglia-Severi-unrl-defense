package it.unibo.unrldef.model.api;

/**
 * An enemy in a strategic game.
 * 
 * @author danilo.maglia@studio.unibo.it
 */
public interface Enemy extends Entity {
    /**
     * @return the health of the enemy.
     */
    double getHealth();

    /**
     * @return the speed of the enemy.
     */
    double getSpeed();

    /**
     * 
     * @return the amount of money that the enemy will drop on death.
     */
    double getDropAmount();

    /**
     * Method that reduces the health of the enemy.
     * 
     * @param amount the amount of health to reduce
     */
    void reduceHealth(double amount);

    /**
     * 
     * @param speed the new speed of the enemy
     */
    void setSpeed(double speed);

    /**
     * Method that resets the speed of the enemy to the default speed.
     */
    void resetSpeed();

    /**
     * @return true if the enemy is dead, false otherwise
     */
    boolean isDead();

    /**
     * 
     * @return true if the enemy has reached the end of the path, false otherwise
     */
    boolean hasReachedEndOfPath();

    /**
     * Method that moves the enemy following the path.
     * 
     * @param time the time elapsed since the last move
     */
    void move(long time);

    /**
     * @return a copy of the enemy.
     */
    Enemy copy();
}
