package it.unibo.unrldef.core;

import java.util.List;
import java.util.LinkedList;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.input.impl.PlayerInput;
import it.unibo.unrldef.model.api.*;
import it.unibo.unrldef.model.impl.*;

public class GameEngine {

    private final PlayerImpl player;
    private final World currentWorld;
    private final Input input;
    private final View view; 


    public GameEngine() {
        this.input = new PlayerInput();
    }

    public void initGame(final String playerName) {
        this.player = new PlayerImpl(playerName, null);
        this.view = new GameView();
    }

    public void setGameWorld(final World world) {
        this.currentWorld = world;
        this.player.setGameMap(world);
    }

    public void GameLoop() {
        long previousFrameStartTime = System.currentTimeMillis();
        while (!this.currentWorld.GameOver()) {
            final long currentFrameStartTime = System.currentTimeMillis();
            final long elapsed = currentFrameStartTime-previousFrameStartTime;
            processInput();
            update(elapsed);
            render();
            previousFrameStartTime = currentFrameStartTime;
        }
    }

    private void processInput() {
        final Position selectedPosition = this.input.getLastHit().getFirst();
        switch(this.input.getLastHit().getSecond()) {
            case TOWER:
                if (this.input.getSelectedName().isPresent()) {
                    this.player.buildNewTower(selectedPosition);
                }
                break;
            case PLACE_SPELL:
                this.player.throwPotion(this.input.getSelectedName().get(), selectedPosition);
                break;
            case NULL:
                break;
        }
    }

    private void update(final long elapsed) {
        this.currentWorld.update(elapsed);
    }

    private void render() {
        this.view.render();
    }
}
