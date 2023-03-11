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
    public PlayerImpl(final String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setGameMap(final World world) {
        this.currentWorld = Objects.requireNonNull(world);
        this.spells.add(new FireBall(world));
        this.spells.add(new Arrows(world));
    }

    @Override
    public World getGameWorld() {
        return this.currentWorld;
    }
    
    @Override
    public void buildNewTower(final Position pos, final String towerName) {
        this.currentWorld.tryBuildTower(pos, towerName);
    }

    @Override
    public void throwSpell(final String name, final Position pos) {
        final Spell selected = this.spells.stream()
                .filter(p -> Objects.equals(p.getName(), name))
                .findFirst().get();
        selected.tryActivation(pos);
    }

    @Override
    public void updateSpellState(final long elapsed) {
        this.spells.forEach(sp -> sp.updateState(elapsed));
    }

    @Override
    public Set<Spell> getActiveSpells() {
        return new HashSet<Spell>(this.spells.stream()
                .filter(sp -> sp.isActive())
                .toList());
    }
}
