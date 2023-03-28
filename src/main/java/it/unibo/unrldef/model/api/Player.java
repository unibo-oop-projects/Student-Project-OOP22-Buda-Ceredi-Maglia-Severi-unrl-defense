package it.unibo.unrldef.model.api;

import java.util.Set;

import it.unibo.unrldef.common.Position;

/**
 * A player in a strategic game where his position is irrelevant and spells can be used.
 * @author tommaso.severi2@studio.unibo.it
 */
public interface Player {
    /**
     * @return the name of the player
     */
    String getName();

    /**
     * @param name the name of the player
     */
    void setName(String name);

    /**
     * Sets the map where the player will be playing.
     * @param map the next map
     */
    void setGameMap(World map);

    /**
     * @return the map where the player is currently playing
     */
    World getGameWorld();

    /**
     * Places a new tower on the world map if the player has enough money.
     * @param pos the position where to place it
     * @param towerName the type of tower to build
     */
    void ifPossibleBuildTower(Position pos, String towerName);

    /**
     * Places a new spell on the world map deling damage to enemies.
     * @param name its name
     * @param pos its position
     */
    void throwSpell(String name, Position pos);

    /**
     * Updates the state of the spells.
     * @param elapsed time passed since last frame
     */
    void updateSpellState(long elapsed);

    /**
     * @return a set containing all the spells that the player can use
     */
    Set<Spell> getSpells();

    /**
     * Sets the spells that the player can use in the game.
     * @param spells a set of spells
     */
    void setSpells(Set<Spell> spells);

}
