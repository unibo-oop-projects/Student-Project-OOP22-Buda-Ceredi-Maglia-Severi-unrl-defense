package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;

public class Orc extends EnemyImpl{
    public static final String NAME = "orc";
    public static final double SPEED = 1.0;
    public static final double HEALTH = 80.0;
    public static final double DROP = 70.0;
    
    public Orc(Optional<Position> position) {
        super(position, Orc.NAME, Orc.HEALTH, Orc.SPEED, Orc.DROP);
    }
}
