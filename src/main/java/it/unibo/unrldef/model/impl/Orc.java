package it.unibo.unrldef.model.impl;

/**
 * An enemy in the game Unreal Defense.
 * 
 * @author danilo.maglia@studio.unibo.it
 */
public class Orc extends EnemyImpl {
    /**
     * The name of the enemy.
     */
    public static final String NAME = "orc";
    /**
     * The speed of the enemy.
     */
    public static final double SPEED = 2.0;
    /**
     * The health of the enemy.
     */
    public static final double HEALTH = 80.0;
    /**
     * The amount of money that the enemy drops when it dies.
     */
    public static final double DROP = 70.0;

    /**
     * Create a new Orc.
     */
    public Orc() {
        super(Orc.NAME, Orc.HEALTH, Orc.SPEED, Orc.DROP);
    }
}
