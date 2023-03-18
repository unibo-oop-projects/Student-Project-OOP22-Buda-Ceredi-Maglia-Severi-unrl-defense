package it.unibo.unrldef.model.api;

import java.util.List;
import java.util.Set;

import it.unibo.unrldef.common.*;

/**
 * the world of a tower defense game
 * @author francesco.buda3@studio.unibo.it
 * 
 */
public interface World {
    void startGame();
    Boolean tryBuildTower(Position pos, String towerName);
    List<Entity> getSceneEntities();
    Integrity getCastleIntegrity();
    Set<Position> getAvailablePositions();
    double getMoney();
    Set<Tower> getAvailableTowers();
    List<Enemy> sorroundingEnemies(Position center, double radius);
    Path getPath();
    boolean isGameOver();
    void updateState(long time);
}
