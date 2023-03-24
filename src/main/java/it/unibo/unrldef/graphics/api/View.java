package it.unibo.unrldef.graphics.api;

import it.unibo.unrldef.model.api.World.GameState;

public interface View {
    /**
     * Render the current state of the game.
     */
    public void render();

    /**
     * Renders the end of the game
     */
    public void renderEndGame(GameState state);
}
