package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.World;

public class Goblin extends EnemyImpl{
    public static final String NAME = "goblin";
    public static final double SPEED = 10.0;
    public static final double HEALTH = 40.0;
    
    public Goblin(Optional<Position> position, World parentWorld) {
        super(position, Goblin.NAME, parentWorld, Goblin.HEALTH, Goblin.SPEED);
    }
}
