package it.unibo.unrldef.model.api;

/**
 * A player in a strategic game where his position is irrelevant
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
    public void setGameMap(World map);

    /**
     * @return the map where the player is currently playing
     */
    public World getGameWorld();
}
