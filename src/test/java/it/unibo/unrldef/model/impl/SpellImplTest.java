package it.unibo.unrldef.model.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.api.Path.Direction;

/**
 * Test class for SpellImpl.
 * 
 * @author tommaso.severi2@studio.unibo.it
 */
class SpellImplTest {

    private static final double TEST_RADIUS = 5;
    private static final double TEST_DAMAGE = 4;
    private final long testRechargeTime = 3 * 1000;
    private final long testLingeringEffectTime = 2 * 1000;
    private final long testLingeringEffectFrequency = 1 * 1000;
    private SpellImpl testSpell;
    private World testWorld;

    /**
     * Initializes the values before each test.
     */
    @BeforeEach
    public void init() {
        this.testWorld = new WorldImpl.Builder("testWorld", new PlayerImpl(), new Position(0, 0), 0, 0)
                .addPathSegment(Direction.END, 0)
                .build();
        this.testSpell = new SpellImpl("test", this.testWorld, TEST_RADIUS, TEST_DAMAGE,
                this.testRechargeTime, this.testLingeringEffectTime, this.testLingeringEffectFrequency) {
            @Override
            protected void effect(final Enemy enemy) {
            }

            @Override
            protected void resetEffect() {
            }
        };
    }

    /**
     * Test if the activation, attack and deactivation methods work.
     */
    @Test
    void testActivation() {
        // Checks if the spell activates before time
        // An empty position is used since it doesn't matter
        final Position testPosition = new Position(0, 0);
        this.testSpell.updateState(this.testRechargeTime - 1 * 1000);
        assert (!this.testSpell.ifPossibleActivate(testPosition));
        this.testSpell.updateState(1 * 1000);
        assert (this.testSpell.isReady());
        // Once ready it spawns an enemy with the same health as the spell damage and
        // places the spell on it
        final Enemy testTarget = new EnemyImpl("test", TEST_DAMAGE, 0, 0);
        this.testWorld.spawnEnemy(testTarget, testPosition);
        assert (this.testSpell.ifPossibleActivate(testTarget.getPosition().get()));
        // Checks if the enemy targeted actually took damage and is now dead
        assert (testTarget.isDead());
        assert (this.testSpell.isActive());
        this.testSpell.updateState(this.testLingeringEffectTime + 1 * 1000);
        // After the spell activation time has passed the spell should deactivate
        assert (!this.testSpell.isActive());
    }
}
