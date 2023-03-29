package it.unibo.unrldef.model.impl;

/**
 * An enemy in the game Unreal Defense.
 * 
 * @author danilo.maglia@studio.unibo.it
 */
public class Goblin extends EnemyImpl {
    /**
     * The name of the enemy.
     */
    public static final String NAME = "goblin";
    /**
     * The speed of the enemy.
     */
    public static final double SPEED = 3.0;
    /**
     * The health of the enemy.
     */
    public static final double HEALTH = 40.0;
    /**
     * The amount of money that the enemy drops when it dies.
     */
    public static final double DROP = 50.0;

    /**
     * Create a new Goblin.
     */
    public Goblin() {
        super(Goblin.NAME, Goblin.HEALTH, Goblin.SPEED, Goblin.DROP);
    }
}
