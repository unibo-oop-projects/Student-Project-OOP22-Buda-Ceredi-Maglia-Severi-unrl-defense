package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Tower;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.api.Path.Direction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for TowerImpl.
 * @author tommaso.ceredi@studio.unibo.it
 */
public class TowerImplTest {

    private World testWorld;
    private final long testAttackForSecond = 1000;
    private final int testCost = 100;
    private Enemy testEnemy;

    /**
     * Initialize the world.
     */
    @BeforeEach
    public void init() {
        this.testWorld = new WorldImpl.Builder("testWorld", new PlayerImpl(), new Position(0, 0), 1, this.testCost)
                .addPathSegment(Direction.END, 0)
                .addAvailableTower(Hunter.NAME, new Hunter())
                .addTowerBuildingSpace(0, 0)
                .build();
    }
}
