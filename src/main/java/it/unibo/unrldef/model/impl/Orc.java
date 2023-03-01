package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.World;

public class Orc extends EnemyImpl{
    private static final String NAME = "orc";
    private static final double SPEED = 5.0;
    public static final double HEALTH = 80.0;
    
    public Orc(Optional<Position> position, World parentWorld) {
        super(position, Orc.NAME, parentWorld, Orc.HEALTH, Orc.SPEED);
    }
}
