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
        this.spawingPoint = new Position(spawningPoint.getX(), spawningPoint.getY());
    }

    /**
     * Create a new path.
     * 
     * @param p
     *          the path to copy
     */
    public PathImpl(final Path p) {
        final PathImpl p2 = (PathImpl) p;
        this.path = new ArrayList<>();
        for (int i = 0; i < p2.size(); i++) {
            this.path.add(p2.getDirection(i));
        }
        this.spawingPoint = new Position(p2.getSpawningPoint().getX(), p2.getSpawningPoint().getY());
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
        return new Position(this.spawingPoint.getX(), this.spawingPoint.getY());
    }

    private int size() {
        return path.size();
    }

}
