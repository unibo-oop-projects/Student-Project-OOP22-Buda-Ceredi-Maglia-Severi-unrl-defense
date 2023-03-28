package it.unibo.unrldef.graphics.api;

import it.unibo.unrldef.model.api.World.GameState;

public interface View {
    /**
     * Render the current state of the game.
     */
    public void render();

    /**
     * Updates the memu of the game
     */
    public void updateMenu();

    /**
     * Starts the rendering of the game
     */
    public void initGame();

    /**
     * Renders the end of the game
     */
    public void renderEndGame(GameState state);
}
