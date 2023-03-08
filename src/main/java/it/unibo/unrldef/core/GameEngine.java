package it.unibo.unrldef.core;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.graphics.api.View;
import it.unibo.unrldef.graphics.impl.ViewImpl;
import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.input.impl.PlayerInput;
import it.unibo.unrldef.model.api.*;
import it.unibo.unrldef.model.impl.*;

public class GameEngine {

    private PlayerImpl player;
    private World currentWorld;
    private final Input input;
    private View view; 


    public GameEngine() {
        this.input = new PlayerInput();
    }

    public void initGame(final String playerName, final World world) {
        this.player = new PlayerImpl(playerName, world);
        this.view = new ViewImpl(world);
    }

    public void setGameWorld(final World world) {
        this.currentWorld = world;
        this.player.setGameMap(world);
    }

    public void GameLoop() {
        long previousFrameStartTime = System.currentTimeMillis();
        while (!this.currentWorld.isGameOver()) {
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
        this.currentWorld.updateState(elapsed);
    }

    private void render() {
        this.view.render();
    }
}
