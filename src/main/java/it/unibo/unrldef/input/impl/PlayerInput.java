package it.unibo.unrldef.input.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.input.api.Input;

public class PlayerInput implements Input{

    private Optional<Pair<Position, HitType>> lastHit;
    private Optional<String> selectedName;

    public PlayerInput() {
        this.lastHit = Optional.empty();
        this.selectedName = Optional.empty();
    }

    @Override
    public void setLastHit(final int x, final int y, final HitType hit, final Optional<String> selected) {
        this.lastHit = Optional.of(new Pair<Position,Input.HitType>(new Position(x, y), hit));
        this.selectedName = selected;
    }

    @Override
    public Optional<Pair<Position, Input.HitType>> getLastHit() {
        final Optional<Pair<Position, HitType>> returnHit = this.lastHit;
        this.lastHit = Optional.empty();
        return returnHit;
    }

    @Override
    public Optional<String> getSelectedName() {
        final Optional<String> returnName = this.selectedName;
        this.selectedName = Optional.empty();
        return returnName;
    }
}
