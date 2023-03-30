package it.unibo.unrldef.model.impl;
import org.junit.jupiter.api.Test;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Path;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.impl.WorldImpl.Builder;

public class EnemyImplTest {
    @Test
    void testMove() {
        final int enemyInitialSpeed = 5;
        final int enemySecondSpeed = 3;
        final int pathMostRight = 10;
        final int pathMostBottom = 10;
        final int SECOND = 1000;
        Path testPath = new PathImpl(new Position(0, 0));
        testPath.addDirection(Path.Direction.DOWN, pathMostBottom);
        testPath.addDirection(Path.Direction.RIGHT, pathMostRight);
        testPath.addDirection(Path.Direction.END, 0);
        // testWorld doesn't need waves, available_towers, bank, and all the other stuff since the only thing it needs is a path
        World testWorld = new WorldImpl.Builder(" ", new PlayerImpl(), new Position(0, 0), 5, 0)
            .addPathSegment(Path.Direction.DOWN, pathMostBottom)
            .addPathSegment(Path.Direction.RIGHT, pathMostRight)
            .addPathSegment(Path.Direction.END, 0).build();
        Enemy enemy = new EnemyImpl("orc", 100, enemyInitialSpeed, 1);
        enemy.setParentWorld(testWorld);
        enemy.setPosition(0, 0);
        enemy.updateState(SECOND);
        assert(enemy.getPosition().get().equals(new Position(0, enemyInitialSpeed)));
        enemy.updateState(SECOND);
        assert(enemy.getPosition().get().equals(new Position(0, pathMostBottom)));
        // I give the enemy a speed of 3 and even then 10 is not divisible by 3 it should still move 4 times before reaching the end and it should not step out of bounds
        enemy.setSpeed(3);
        enemy.updateState(SECOND);
        assert(enemy.getPosition().get().equals(new Position(enemySecondSpeed, pathMostBottom)));
        enemy.updateState(SECOND);
        assert(enemy.getPosition().get().equals(new Position(enemySecondSpeed * 2, pathMostBottom)));
        enemy.updateState(SECOND);
        assert(enemy.getPosition().get().equals(new Position(enemySecondSpeed * 3, pathMostBottom)));
        enemy.updateState(SECOND);
        assert(enemy.getPosition().get().equals(new Position(pathMostRight, pathMostBottom)));
        // The enemy should now be at the end of the path
        enemy.updateState(SECOND);
        assert(enemy.hasReachedEndOfPath());

    }
}
