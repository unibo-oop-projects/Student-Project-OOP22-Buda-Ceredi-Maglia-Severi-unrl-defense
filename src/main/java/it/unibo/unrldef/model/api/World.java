package it.unibo.unrldef.model.api;

import java.util.List;
import java.util.Set;

import it.unibo.unrldef.common.*;
import it.unibo.unrldef.model.impl.EntityImpl;

/**
 * the world of a tower defense game
 * @author francesco.buda3@studio.unibo.it
 */
public interface World {
    void startGame();
    void buildTower(Position pos, Tower tower);
    List<EntityImpl> getSceneEntities();
    Integrity getCastleIntegrity();
    int getMoney();
    Set<Tower> getAvailableTowers();
    Path getPath();
}
