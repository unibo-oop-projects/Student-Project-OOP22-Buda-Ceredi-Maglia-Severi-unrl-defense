package it.unibo.unrldef.input.impl;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.input.api.Input;

public class PlayerInput implements Input{

    private Pair<Position, HitType> lastHit;

    public PlayerInput() {
        this.lastHit = null;
    }

    @Override
    public void setLastHit(int x, int y, HitType hit) {
        this.lastHit = new Pair<Position,Input.HitType>(new Position(x, y), hit);
    }

    @Override
    public Pair<Position, Input.HitType> getLastHit() {
        return this.lastHit;
    }
    
}
