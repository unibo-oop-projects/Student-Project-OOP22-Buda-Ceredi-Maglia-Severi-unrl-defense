package it.unibo.unrldef.model.impl;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.World;

/**
 * Test class for WorldImpl.
 */
public class WorldImplTest {

    private World testWorld;

    /**
     * Initialize the world.
     */
    @BeforeEach
    public void init() {
        final LevelBuilder testLevel = new LevelBuilder(new PlayerImpl());
        this.testWorld = testLevel.levelOne();
    }

    @Test
    void testEnemiesInRange() {
        final int testX1 = 14;
        final int testY1 = 14;
        final int testX2 = 10;
        final int testY2 = 24;
        final int testX3 = 4;
        final int testY3 = 4;
        final int testX4 = 16;
        final int testY4 = 34;
        final int testX5 = 34;
        final int testY5 = 68;
        final int testX6 = 18;
        final int testY6 = 24;

        Position testPos1 = new Position(testX1, testY1);
        Position testPos2 = new Position(testX2, testY2);
        Position testPos3 = new Position(testX3, testY3);
        Position testPos4 = new Position(testX4, testY4);
        Position testPos5 = new Position(testX5, testY5);
        Position testPos6 = new Position(testX6, testY6);

        Enemy testEnemy1 = new Orc();
        Enemy testEnemy2 = new Orc();
        Enemy testEnemy3 = new Goblin();
        Enemy testEnemy4 = new Goblin();
        Enemy testEnemy5 = new Goblin();

        final int testRadius = 14;

        this.testWorld.spawnEnemy(testEnemy1, testPos1); //spawning the first enemy in the path
        this.testWorld.spawnEnemy(testEnemy2, testPos2); //spawning the second enemy in the path
        this.testWorld.spawnEnemy(testEnemy3, testPos3); //spawning the third enemy out of the path
        this.testWorld.spawnEnemy(testEnemy4, testPos4); //spawning the fourth enemy in the path and more advanced than the others
        this.testWorld.spawnEnemy(testEnemy5, testPos5); //spawning the fifth enemy in the path, but too far 

        var sorroundingEnemies = this.testWorld.sorroundingEnemies(testPos6, testRadius);

        assert (sorroundingEnemies.containsAll(List.of(testEnemy1, testEnemy2, testEnemy4)));
        assert (!sorroundingEnemies.contains(testEnemy3));
        assert (!sorroundingEnemies.contains(testEnemy5));
        assert (sorroundingEnemies.get(0).equals(testEnemy4));
    }
}
