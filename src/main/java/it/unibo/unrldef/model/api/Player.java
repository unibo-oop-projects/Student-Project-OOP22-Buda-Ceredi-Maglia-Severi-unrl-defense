package it.unibo.unrldef.model.api;

public interface Player {
    
    public String getName();

    public void setGameMap(World map);

    public World getGameWorld();
}
