package it.unibo.unrldef.model.api;

import java.util.Optional;
import it.unibo.unrldef.common.Pair;

/**
 * A wave of enemies' hordes in a strategic game .
 * @author danilo.maglia@studio.unibo.it
 */
public interface Wave {
    /**
     * @return the next horde of the wave
     */
    Optional<Pair<Horde, Long>> getNextHorde();

    /**
     * Adds an horde to the wave.
     * @param horde the horde to add
     * @param secondsToSpawn the seconds that this hordes need to spawn
     */
    void addHorde(Horde horde, long secondsToSpawn);

    /**
     * 
     * @return true if there is another horde, false otherwise
     */
    boolean isWaveOver();

}
