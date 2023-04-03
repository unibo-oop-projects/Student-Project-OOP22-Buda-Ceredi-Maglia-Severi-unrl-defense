package it.unibo.unrldef.core.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.core.api.GameEngine;
import it.unibo.unrldef.graphics.api.View;
import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.input.impl.PlayerInput;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.api.World.GameState;
import it.unibo.unrldef.graphics.impl.ViewImpl;

/**
 * This class modules the engine that updates the game.
 * @author tommaso.severi2@studio.unibo.it
 */
public final class GameEngineImpl implements GameEngine {

    private final long period = 1000 / 30;
    private final Player player;
    private World currentWorld;
    private final Input input;
    private final View gameView; 
    private boolean started;
    private boolean ended;

    /**
     * Builds a new GameEngine.
     * @param world the world of the game
     * @param player the player of the game
     */
    public GameEngineImpl(final World world, final Player player) {
        this.input = new PlayerInput();
        this.gameView = new ViewImpl(player, world, input);
        this.player = player;
        this.setGameWorld(world);
        this.started = false;
        this.ended = false;
    }

    @Override
    public void initGame(final String playerName) {
        this.player.setName(playerName);
        this.gameView.initGame();
        this.started = true;
    }

    @Override
    public void setGameWorld(final World world) {
        this.currentWorld = world;
    }

    @Override
    public void menuLoop() {
        while (!started) {
            this.processInput();
            this.gameView.updateMenu();
        }
        this.gameLoop();
    }

    @Override
    public void gameLoop() {
        long previousFrameStartTime = System.currentTimeMillis();
        while (this.isGameRunning()) {
            final long currentFrameStartTime = System.currentTimeMillis();
            final long elapsed = currentFrameStartTime - previousFrameStartTime;
            this.processInput();
            this.update(elapsed);
            this.render();
            this.waitForNextFrame(currentFrameStartTime);
            previousFrameStartTime = currentFrameStartTime;
        }
        this.endLoop();
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
                    this.player.buildTower(selectedPosition, selectedName.get());
                    break;
                case PLACE_SPELL:
                    this.player.throwSpell(selectedPosition, selectedName.get());
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
     * Checks if the game is running.
     * @return true if the game is running, false otherwise
     */
    private boolean isGameRunning() {
        return this.gameState().equals(GameState.PLAYING) && !this.ended;
    }

    /**
     * @return the current state of the game
     */
    private GameState gameState() {
        return this.currentWorld.gameState();
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
     * Starts the end loop.
     */
    private void endLoop() {
        while (!ended) {
            this.renderEndState(this.gameState());
            this.processInput();
        }
    }

    /**
     * Exits the game.
     */
    private void exitGame() {
        this.ended = true;
        System.exit(0);
    }

    /**
     * Renders the end of the game.
     * @param state the final state of the game
     */
    private void renderEndState(final GameState state) {
        this.gameView.renderEndGame(state);
    }
}
