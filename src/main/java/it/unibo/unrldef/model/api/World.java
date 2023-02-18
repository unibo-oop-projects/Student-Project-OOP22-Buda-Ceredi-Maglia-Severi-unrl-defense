package it.unibo.unrldef.model.api;

import java.util.List;
import java.util.Set;

import it.unibo.unrldef.common.*;

public interface World {
    void startWave(Wave wave);
    void buildTower(Position pos, Tower tower);
    List<GameObject> getSceneEntities();
    TargetIntegrity getCastleIntegrity();
    int getMoney();
    Set<Tower> getAvailableTowers();
    Path getPath();
}
