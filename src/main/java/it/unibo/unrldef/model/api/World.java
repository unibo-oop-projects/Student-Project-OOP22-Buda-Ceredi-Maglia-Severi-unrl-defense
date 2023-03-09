package it.unibo.unrldef.model.api;

import java.util.List;
import java.util.Map;

import it.unibo.unrldef.common.*;

/**
 * the world of a tower defense game
 * @author francesco.buda3@studio.unibo.it
 * 
 */
public interface World {
    void startGame();
    void buildTower(Position pos, String towerName);
    List<Entity> getSceneEntities();
    Integrity getCastleIntegrity();
    int getMoney();
    Map<String, Tower> getAvailableTowers();
    List<Enemy> sorroundingEnemies(Position center, double radius);
    Path getPath();
    boolean isGameOver();
    void updateState(long time);
}
