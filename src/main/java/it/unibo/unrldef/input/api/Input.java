package it.unibo.unrldef.input.api;

import java.util.Optional;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;

/**
 * Interface to handle the input of the player.
 */
public interface Input {
    /**
     * Enumeration of the possible hit types.
     */
    enum HitType {
        /**
         * The player has selected on the start button.
         */
        START_GAME,
        /**
         * The player has selected on the exit button.
         */
        EXIT_GAME,
        /**
         * The player has selected on a tower button.
         */
        PLACE_TOWER,
        /**
         * The player has selected on a spell button.
         */
        PLACE_SPELL,
        /**
         * The default selection of the player.
         */
        SELECTION
    }

    /**
     * Sets the informations of the last hit of the player.
     * @param x the x coordinate of the hit
     * @param y the y coordinate of the hit
     * @param hit the type of the hit
     * @param selected the name selected fo a tower,spell or for the player
     */
    void setLastHit(int x, int y, HitType hit, Optional<String> selected);

    /**
     * Returns the informations of the last hit of the player.
     * @return an optional of a pair containing the position and the type of the hit
     */
    Optional<Pair<Position, Input.HitType>> getLastHit();

    /**
     * Returns the selected name.
     * @return an optional of the selected name
     */
    Optional<String> getSelectedName();
}
