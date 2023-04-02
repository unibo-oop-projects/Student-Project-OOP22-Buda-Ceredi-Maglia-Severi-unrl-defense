package it.unibo.unrldef.core.api;

import it.unibo.unrldef.model.api.World;

/**
 * This interface models the engine that updates the game.
 */
public interface GameEngine {

    /**
     * Sets the world of the game.
     * @param world the world of the game
     */
    void setGameWorld(World world);

    /**
     * Initializes the game.
     * @param playerName the name of the player
     */
    void initGame(String playerName);

    /**
     * Starts the menu loop.
     */
    void menuLoop();

    /**
     * Starts the game loop.
     */
    void gameLoop();
}
