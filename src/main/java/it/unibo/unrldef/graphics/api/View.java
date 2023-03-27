package it.unibo.unrldef.graphics.api;

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
}
