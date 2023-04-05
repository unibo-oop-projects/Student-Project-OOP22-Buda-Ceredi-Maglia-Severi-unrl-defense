package it.unibo.unrldef.core.api;

import it.unibo.unrldef.graphics.api.View;
import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.World;

/**
 * This interface models the engine that updates the game.
 * 
 * @author tommaso.severi2@studio.unibo.it
 */
public interface GameEngine {

    /**
     * Sets the world of the game.
     * @param world the world of the game
     */
    void setGameWorld(World world);

    /**
     * Sets the player of the game.
     * @param player the player of the game
     */
    void setPlayer(Player player);

    /**
     * Sets the view of the game.
     * @param view the view of the game
     */
    void setView(View view);

    /**
     * Sets the input of the game.
     * @param input the input of the game
     */
    void setInput(Input input);

    /**
     * Initializes the game.
     * @param playerName the name of the player
     */
    void initGame(String playerName);

    /**
     * Starts the game loop.
     */
    void gameLoop();
}
