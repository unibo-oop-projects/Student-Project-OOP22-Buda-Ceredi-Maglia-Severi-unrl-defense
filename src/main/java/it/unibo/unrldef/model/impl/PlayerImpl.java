package it.unibo.unrldef.model.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.Potion;

public class PlayerImpl implements Player{

    private World map;
    private final String name;
    private final Set<Potion> potions = new HashSet<>();

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
    
    public void buildNewTower(int x, int y) {
        this.map.addTower(x, y);
    }

    public void throwPotion(String name, int x, int y) {
        final Potion selected = this.potions.stream()
                .filter(p -> Objects.equals(p.getName(), name))
                .findFirst().get();
        if (selected.isReady()) {
            this.map.addPotion(selected, x, y);
        }
    }
}
