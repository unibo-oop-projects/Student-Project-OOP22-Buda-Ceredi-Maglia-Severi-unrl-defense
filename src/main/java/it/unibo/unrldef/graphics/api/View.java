package it.unibo.unrldef.graphics.api;

import it.unibo.unrldef.model.api.World.GameState;

/**
 * Models the view of the game.
 * @author danilo.maglia@studio.unibo.it
 * @author tommaso.severi2@studio.unibo.it
 */
public interface View {
    /**
     * Render the current state of the game.
     */
    void render();

    /**
     * Updates the memu of the game.
     */
    void updateMenu();

    /**
     * Starts the rendering of the game.
     */
    void initGame();

    /**
     * Renders the end of the game.
     * @param state the state of the game
     */
    void renderEndGame(GameState state);

    /**
     * Exits the game render.
     */
    void exitGame();
}
