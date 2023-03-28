package it.unibo.unrldef.core;

import java.util.Optional;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.graphics.api.View;
import it.unibo.unrldef.graphics.impl.ViewImpl;
import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.input.impl.PlayerInput;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.api.World.GameState;

/**
 * This class modules the engine that updates the game.
 */
public class GameEngine {

    private final long period = 1000 / 30;
    private final Player player;
    private World currentWorld;
    private final Input input;
    private final View gameView; 
    private boolean started;

    /**
     * Builds a new GameEngine.
     * @param world the world of the game
     * @param player the player of the game
     */
    public GameEngine(final World world, final Player player) {
        this.input = new PlayerInput();
        this.player = player;
        this.currentWorld = world;
        this.gameView = new ViewImpl(player, this.currentWorld, this.input);
        this.started = false;
    }

    /**
     * Initializes the game.
     * @param playerName the name of the player
     */
    public void initGame(final String playerName) {
        this.player.setName(playerName);
        this.gameView.initGame();
        this.started = true;
    }

    /**
     * Sets the world of the game.
     * @param world the world of the game
     */
    public void setGameWorld(final World world) {
        this.currentWorld = world;
    }

    /**
     * Starts the game loop.
     */
    public void gameLoop() {
        while (!started) {
            this.processInput();
            this.gameView.updateMenu();
        }
        long previousFrameStartTime = System.currentTimeMillis();
        while (this.currentWorld.gameState() == GameState.PLAYING) {
            final long currentFrameStartTime = System.currentTimeMillis();
            final long elapsed = currentFrameStartTime - previousFrameStartTime;
            this.processInput();
            this.update(elapsed);
            this.render();
            this.waitForNextFrame(currentFrameStartTime);
            previousFrameStartTime = currentFrameStartTime;
        }
        this.endOfGame(this.currentWorld.gameState());
    }

    /**
     * Waits for the next frame.
     * @param cycleStartTime the start time of the cycle
     */
    private void waitForNextFrame(final long cycleStartTime) {
        final long elapsed = System.currentTimeMillis() - cycleStartTime;
        if (elapsed < this.period) {
            try {
                Thread.sleep(period - elapsed);
            } catch (InterruptedException e) {
                e.printStackTrace(); // NOPMD if this fails the game has to stop
            }
        }
    }

    /**
     * Processes the input.
     */
    private void processInput() {
        final Optional<Pair<Position, Input.HitType>> lastHit = input.getLastHit();
        if (lastHit.isPresent()) {
            final Position selectedPosition = lastHit.get().getFirst();
            final Optional<String> selectedName = this.input.getSelectedName();
            switch (lastHit.get().getSecond()) {
                case PLACE_TOWER:
                    this.currentWorld.tryBuildTower(selectedPosition, selectedName.get());
                    break;
                case PLACE_SPELL:
                    this.player.throwSpell(selectedName.get(), selectedPosition);
                    break;
                case START_GAME:
                    this.initGame(selectedName.get());
                    break;
                case EXIT_GAME:
                    this.exitGame();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Updates the game world.
     * @param elapsed the elapsed time since last frame
     */
    private void update(final long elapsed) {
        this.currentWorld.updateState(elapsed);
    }

    /**
     * Renders the game.
     */
    private void render() {
        this.gameView.render();
    }

    /**
     * Exits the game.
     */
    private void exitGame() {
        System.exit(0);
    }

    /**
     * Renders the end of the game.
     * @param state the final state of the game
     */
    private void endOfGame(final GameState state) {
        this.gameView.renderEndGame(state);
    }
}
