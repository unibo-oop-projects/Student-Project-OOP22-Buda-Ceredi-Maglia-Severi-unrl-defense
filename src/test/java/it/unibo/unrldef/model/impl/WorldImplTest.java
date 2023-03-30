package it.unibo.unrldef.model.impl;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.World;

public class WorldImplTest {

    private World testWorld;

    @BeforeEach
    public void init() {
        final LevelBuilder testLevel = new LevelBuilder(new PlayerImpl());
        this.testWorld = testLevel.levelOne();
    }

    @Test
    void testEnemiesInRange() {
        Position testPos1 = new Position(14, 14);
        Position testPos2 = new Position(10, 24);
        Position testPos3 = new Position(4, 4);
        Position testPos4 = new Position(16, 34);
        Position testPos5 = new Position(34, 68);
        Position testPos6 = new Position(18, 24);

        Enemy testEnemy1 = new Orc();
        Enemy testEnemy2 = new Orc();
        Enemy testEnemy3 = new Goblin();
        Enemy testEnemy4 = new Goblin();
        Enemy testEnemy5 = new Goblin();

        this.testWorld.spawnEnemy(testEnemy1, testPos1); //spawning the first enemy in the path
        this.testWorld.spawnEnemy(testEnemy2, testPos2); //spawning the second enemy in the path
        this.testWorld.spawnEnemy(testEnemy3, testPos3); //spawning the third enemy out of the path
        this.testWorld.spawnEnemy(testEnemy4, testPos4); //spawning the fourth enemy in the path and more advanced than the others
        this.testWorld.spawnEnemy(testEnemy5, testPos5); //spawning the fifth enemy in the path, but too far 

        var sorroundingEnemies = this.testWorld.sorroundingEnemies(testPos6, 14);

        assert(sorroundingEnemies.containsAll(List.of(testEnemy1, testEnemy2, testEnemy4)));
        assert(!sorroundingEnemies.contains(testEnemy3));
        assert(!sorroundingEnemies.contains(testEnemy5));
        assert(sorroundingEnemies.get(0).equals(testEnemy4));
    }
}
