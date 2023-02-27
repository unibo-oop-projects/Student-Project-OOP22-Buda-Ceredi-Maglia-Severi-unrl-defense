package it.unibo.unrldef.model.api;

import java.util.List;

/**
 * An enemy horde in a strategic game
 * @author danilo.maglia@studio.unibo.it
 */
public interface Horde {
    /**
     * 
     * @return a list of enemy in the horde
     */
    public List<Enemy> getEnemies(); 
}
