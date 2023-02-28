package it.unibo.unrldef.model.api;

import java.util.List;

/**
 * An enemy horde in a strategic game
 * @author danilo.maglia@studio.unibo.it
 */
public interface Horde {
    /**
     * 
     * @return a list of enemies in the horde
     */
    public List<Enemy> getEnemies(); 
    /**
     * Add a copy of the enemy passed to the horde
     * @param enemy enemy that will be added to the horde
     */
    public void addEnemy(Enemy enemy);
    /**
     * Add multiple copies of the enemy passed to the horde
     * @param enemy enemy that will be added to the horde
     * @param numberOfEnemy number of enemies that will be added
     */
    public void addMultipleEnemies(Enemy enemy, short numberOfEnemy);
}
