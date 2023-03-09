package it.unibo.unrldef.model.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.Spell;
import it.unibo.unrldef.model.api.World;

/**
 * The main player in a tower defense game
 * @author tommaso.severi2@studio.unibo.it
 */
public class PlayerImpl implements Player{

    private World currentWorld;
    private final String name;
    private final Set<Spell> spells = new HashSet<>();

    /**
     * Crates a new Player
     * @param name its name
     * @param map where he begins to play
     */
    public PlayerImpl(final String name, final World world) {
        this.setGameMap(world);
        this.name = Objects.requireNonNull(name);
        this.spells.add(new FireBall(world));
        this.spells.add(new Arrows(world));
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setGameMap(final World world) {
        this.currentWorld = Objects.requireNonNull(world);
    }

    @Override
    public World getGameWorld() {
        return this.currentWorld;
    }
    
    /**
     * Places a new tower on the world map
     * @param pos the position where to place it
     * @param towerName the type of tower to build
     */
    public void buildNewTower(final Position pos, final String towerName) {
        this.currentWorld.buildTower(pos, towerName);
    }

    /**
     * Places a new spell on the world map deling damage to enemies
     * @param name its name
     * @param pos its position
     */
    public void throwSpell(final String name, final Position pos) {
        final Spell selected = this.spells.stream()
                .filter(p -> Objects.equals(p.getName(), name))
                .findFirst().get();
        selected.tryActivation(pos);
    }

    /**
     * Updates the state of the spells
     * @param elapsed time passed since last frame
     */
    public void updateSpellState(final long elapsed) {
        this.spells.forEach(sp -> sp.updateState(elapsed));
    }
}
