package it.unibo.unrldef.model.api;

import java.util.List;
import java.util.Set;

import it.unibo.unrldef.common.*;
import it.unibo.unrldef.model.impl.Entity;

/**
 * the world of a tower defense game
 * @author francesco.buda3@studio.unibo.it
 * 
 */
public interface World {
    void startGame();
    void buildTower(Position pos, Tower tower);
    List<Entity> getSceneEntities();
    Integrity getCastleIntegrity();
    int getMoney();
    Set<Tower> getAvailableTowers();
    List<Enemy> sorroundingEnemies(Position center, float radius);
    Path getPath();
}
