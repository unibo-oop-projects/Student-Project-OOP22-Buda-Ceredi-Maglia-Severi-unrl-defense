package it.unibo.unrldef.model.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.Spell;
import it.unibo.unrldef.model.api.World;

/**
 * The main player in a tower defense game.
 * @author tommaso.severi2@studio.unibo.it
 */
public final class PlayerImpl implements Player {

    private World currentWorld;
    private String name;
    private Set<Spell> spells;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(final String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public void setGameMap(final World world) {
        this.currentWorld = Objects.requireNonNull(world);
    }

    @Override
    public World getGameWorld() {
        return this.currentWorld;
    }

    @Override
    public boolean buildTower(final Position pos, final String towerName) {
        return this.currentWorld.tryBuildTower(pos, towerName);
    }

    @Override
    public boolean throwSpell(final Position pos, final String name) {
        return this.spells.stream()
                .filter(p -> Objects.equals(p.getName(), name))
                .findFirst().get()
                .ifPossibleActivate(pos);
    }

    @Override
    public void updateSpellState(final long elapsed) {
        this.spells.forEach(sp -> sp.updateState(elapsed));
    }

    /**
     * @return a set of spells that are active
     */
    public Set<Spell> getActiveSpells() {
        return new HashSet<Spell>(this.spells.stream()
                .filter(sp -> sp.isActive())
                .toList());
    }

    @Override
    public Set<Spell> getSpells() {
        return Set.copyOf(this.spells);
    }

    @Override
    public void setSpells(final Set<Spell> spells) {
        this.spells = spells;
    }
}
