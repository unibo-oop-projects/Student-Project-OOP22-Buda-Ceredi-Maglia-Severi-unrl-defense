package it.unibo.unrldef.input.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.input.api.Input;

public class PlayerInput implements Input{

    private Pair<Position, HitType> lastHit;
    private Optional<String> selectedName;

    public PlayerInput() {
        this.lastHit = null;
        this.selectedName = Optional.empty();
    }

    @Override
    public void setLastHit(final int x, final int y, final HitType hit, final Optional<String> selected) {
        System.out.println("PlayerInput: setLastHit(" + x + ", " + y + ", " + hit + ", " + selected + ")");
        this.lastHit = new Pair<Position,Input.HitType>(new Position(x, y), hit);
        this.selectedName = selected;
    }

    @Override
    public Pair<Position, Input.HitType> getLastHit() {
        return this.lastHit;
    }

    @Override
    public Optional<String> getSelectedName() {
        return this.selectedName;
    }
}
