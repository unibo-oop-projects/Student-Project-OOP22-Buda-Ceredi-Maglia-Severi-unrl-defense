package it.unibo.unrldef.model.impl;

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
    void testEneiesInRange () {
        Position testPos1 = new Position(14, 14);
        Position testPos2 = new Position(10, 24);
        Position testPos3 = new Position(4, 4);
        Position testPos4 = new Position(16, 34);

        Enemy testOrc1 = new Orc();
        testOrc1.setPosition(testPos1.getX(), testPos1.getY());
        testOrc1.setParentWorld(this.testWorld);

        Enemy testOrc2 = new Orc();
        testOrc2.setPosition(testPos2.getX(), testPos2.getY());
        testOrc2.setParentWorld(this.testWorld);

        Enemy testGoblin1 = new Goblin();
        testGoblin1.setPosition(testPos3.getX(), testPos3.getY());
        testGoblin1.setParentWorld(this.testWorld);

        Enemy testGoblin2 = new Goblin();
        testGoblin2.setPosition(testPos4.getX(), testPos4.getY());
        testGoblin2.setParentWorld(this.testWorld);
    }
}
