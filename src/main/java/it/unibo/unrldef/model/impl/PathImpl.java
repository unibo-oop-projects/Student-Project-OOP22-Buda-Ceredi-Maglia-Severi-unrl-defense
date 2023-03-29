package it.unibo.unrldef.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Path;

/**
 * Implementation of a path in the game Unreal Defense.
 * 
 * @author danilo.maglia@studio.unibo.it
 */
public final class PathImpl implements Path {
    private final List<Pair<Path.Direction, Double>> path;
    private final Position spawingPoint;

    /**
     * Create a new path.
     * 
     * @param spawningPoint
     *                      the position where the enemies will spawn
     */
    public PathImpl(final Position spawningPoint) {
        this.path = new ArrayList<>();
        this.spawingPoint = spawningPoint;
    }

    @Override
    public Pair<Direction, Double> getDirection(final int index) {
        return path.get(index).copy();
    }

    @Override
    public void addDirection(final Direction direction, final double units) {
        path.add(new Pair<>(direction, units));
    }

    @Override
    public Position getSpawningPoint() {
        return this.spawingPoint;
    }

}
