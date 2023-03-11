package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;

public class Goblin extends EnemyImpl{
    public static final String NAME = "goblin";
    public static final double SPEED = 10.0;
    public static final double HEALTH = 40.0;
    
    public Goblin(Optional<Position> position) {
        super(position, Goblin.NAME, Goblin.HEALTH, Goblin.SPEED);
    }
}
