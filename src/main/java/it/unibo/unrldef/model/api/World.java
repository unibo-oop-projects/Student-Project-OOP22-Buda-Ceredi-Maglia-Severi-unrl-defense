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

    public enum GameState {
        PLAYING,
        VICTORY,
        DEFEAT
    }

    /**
     * @param pos the position of the tower to build
     * @param towerName the name of the tower to build
     * @return true if the construction of the tower was successful
     */
    Boolean tryBuildTower(Position pos, String towerName);

    /**
     * 
     * @return the list of the entities in the world
     */
    List<Entity> getSceneEntities();

    /**
     * 
     * @return the castle's integrity
     */
    Integrity getCastleIntegrity();

    /**
     * 
     * @return a set of the available positions
     */
    Set<Position> getAvailablePositions();

    /**
     * 
     * @return money in the bank
     */
    double getMoney();

    /**
     * 
     * @return a set of the available towers
     */
    Set<Tower> getAvailableTowers();

    /**
     * 
     * @param center the center of the circle
     * @param radius the radius of the circle
     * @return the enemies sorrounding the center
     */
    List<Enemy> sorroundingEnemies(Position center, double radius);

    /**
     * 
     * @return the world's path
     */
    Path getPath();

    /**
     * 
     * @return the state of the world
     */
    GameState gameState();

    /**
     * update the state of the world
     * @param time time elapsed since last update
     */
    void updateState(long time);
}
