package it.unibo.unrldef.model.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.Potion;

/**
 * The main player in a tower defense game
 * @author tommaso.severi2@studio.unibo.it
 */
public class PlayerImpl implements Player{

    private World map;
    private final String name;
    private final Set<Potion> potions = new HashSet<>();

    /**
     * Crates a new Player
     * @param name its name
     * @param map where he begins to play
     */
    public PlayerImpl(String name, World map) {
        this.map = Objects.requireNonNull(map);
        this.name = Objects.requireNonNull(name);
        this.potions.add(new FireBall());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setGameMap(World map) {
        this.map = Objects.requireNonNull(map);
    }

    @Override
    public World getGameWorld() {
        return this.map;
    }
    
    /**
     * Places a new tower on the world map
     * @param pos the position where to place it  
     */
    public void buildNewTower(Position pos) {
        this.map.addTower(pos);
    }

    /**
     * Places a new potion on the world map deling damage to enemies
     * @param name its name
     * @param pos its position
     */
    public void throwPotion(String name, Position pos) {
        final Potion selected = this.potions.stream()
                .filter(p -> Objects.equals(p.getName(), name))
                .findFirst().get();
        if (selected.isReady()) {
            this.map.addPotion(selected, pos);
        }
    }
}
