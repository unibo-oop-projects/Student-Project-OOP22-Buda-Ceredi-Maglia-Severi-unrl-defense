package it.unibo.unrldef.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Path;

/**
 * Implementation of a path in the game Unreal Defense
 * @author danilo.maglia@studio.unibo.it
 */
public class PathImpl implements Path{
    private List<Pair<Path.Direction, Double>> path;
    private final Position spawingPoint;

    public PathImpl(Position spawningPoint) {
        this.path = new ArrayList<Pair<Path.Direction, Double>>();
        this.spawingPoint = spawningPoint;
    }

    @Override
    public Pair<Direction, Double> getDirection(int index) throws IndexOutOfBoundsException{
        return path.get(index).copy();
    }

    @Override
    public void addDirection(Direction direction, double units) {
        path.add(new Pair<Path.Direction,Double>(direction, units));   
    }

    @Override
    public Position getSpawningPoint() {
        return this.spawingPoint;
    }
    
}
