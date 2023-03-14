package it.unibo.unrldef.input.api;

import java.util.Optional;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;

public interface Input {
    
    public enum HitType {
        PLACE_TOWER, PLACE_SPELL, SELECTION
    }

    public void setLastHit(int x, int y, HitType hit, Optional<String> selected);

    public Optional<Pair<Position, Input.HitType>> getLastHit();

    public Optional<String> getSelectedName();
}
