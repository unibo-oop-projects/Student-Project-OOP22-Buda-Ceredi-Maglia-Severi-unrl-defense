package it.unibo.unrldef.model.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.World;

/**
 * Test class for SpellImpl.
 * @author tommaso.severi2@studio.unibo.it
 */
public class SpellImplTest {

    private final double testRadius = 5;
    private final double testDamage = 4;
    private final long testRechargeTime = 3 * 1000;
    private final long testLingeringEffectTime = 2 * 1000;
    private final long testLingeringEffectFrequency = 1 * 1000;
    private SpellImpl testSpell;
    private World testWorld;

    @BeforeEach
    public void init() {
        final LevelBuilder testLevel = new LevelBuilder(new PlayerImpl());
        this.testWorld = testLevel.levelOne();
        this.testSpell = new SpellImpl("test", this.testWorld, this.testRadius, this.testDamage, 
                this.testRechargeTime, this.testLingeringEffectTime, this.testLingeringEffectFrequency) {
            @Override
            protected void effect(Enemy enemy) { }

            @Override
            protected void resetEffect() { }
        };
    }

    /**
     * Test if the activation, attack and deactivation methods work.
     */
    @Test
    void testIfPossibleActivate() {
        final Position testPosition = new Position(0, 0);
        this.testWorld.updateState(this.testRechargeTime - 1 * 1000);
        this.testSpell.updateState(this.testRechargeTime - 1 * 1000);
        assert(!this.testSpell.ifPossibleActivate(testPosition));
        this.testWorld.updateState(1 * 1000);
        this.testSpell.updateState(1 * 1000);
        assert(this.testSpell.isReady());
        final Enemy testTarget = (Enemy) this.testWorld.getSceneEntities().stream()
                .filter(e -> e instanceof Enemy)
                .findAny().get();
        final double testTargetInitialHealth = testTarget.getHealth();
        assert(this.testSpell.ifPossibleActivate(testTarget.getPosition().get()));
        assert(testTarget.getHealth() != testTargetInitialHealth);
        assert(this.testSpell.isActive());
        this.testSpell.updateState(this.testLingeringEffectTime + 1 * 1000);
        assert(!this.testSpell.isActive());
    }
}
