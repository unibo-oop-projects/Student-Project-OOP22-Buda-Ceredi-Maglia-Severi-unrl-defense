package it.unibo.unrldef.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Horde;

/**
 * Implementation of a horde of enemies in the game Unreal Defense.
 * 
 * @author danilo.maglia@studio.unibo.it
 */
public final class HordeImpl implements Horde {

    private final List<Enemy> enemies;

    /**
     * Create a new horde.
     */
    public HordeImpl() {
        this.enemies = new ArrayList<>();
    }

    @Override
    public List<Enemy> getEnemies() {
        return this.enemies;
    }

    @Override
    public void addEnemy(final Enemy enemy) {
        this.enemies.add(enemy.copy());

    }

    @Override
    public void addMultipleEnemies(final Enemy enemy, final short numberOfEnemy) {
        for (int i = 0; i < numberOfEnemy; i++) {
            this.addEnemy(enemy);
        }
    }

}
