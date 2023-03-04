package it.unibo.unrldef.input.api;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;

public interface Input {
    
    public enum HitType {
        TOWER, POTION, NULL
    }

    public void setLastHit(final int x, final int y, final HitType hit);

    public Pair<Position, Input.HitType> getLastHit();
}
