package it.unibo.unrldef.model.api;

import java.util.Set;

import it.unibo.unrldef.common.Position;

/**
 * A player in a strategic game where his position is irrelevant and spells can be used
 * @author tommaso.severi2@studio.unibo.it
 */
public interface Player {
    
    /**
     * @return the name of the player
     */
    public String getName();

    /**
     * Sets the map where the player will be playing
     * @param map the next map
     */
    public void setGameMap(final World map);

    /**
     * @return the map where the player is currently playing
     */
    public World getGameWorld();

    /**
     * Places a new tower on the world map
     * @param pos the position where to place it
     * @param towerName the type of tower to build
     */
    public void buildNewTower(final Position pos, final String towerName);

    /**
     * Places a new spell on the world map deling damage to enemies
     * @param name its name
     * @param pos its position
     */
    public void throwSpell(final String name, final Position pos);

    /**
     * Updates the state of the spells
     * @param elapsed time passed since last frame
     */
    public void updateSpellState(final long elapsed);

    /**
     * @return a set of only the spells that have been activated
     */
    public Set<Spell> getActiveSpells();

    /**
     * @return a set of all the spells the player can use
     */
    public Set<Spell> getSpells();
}
