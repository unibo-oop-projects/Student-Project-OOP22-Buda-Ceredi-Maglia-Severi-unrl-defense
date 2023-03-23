package it.unibo.unrldef.graphics.impl;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;

import java.awt.Image;


public class Sprite {
    private final Pair<Integer, Integer> dim;
    private Pair<Integer, Integer> scaledDim;
    private final Image sprite;
    private Image scaledSprite;

    public Sprite(int width, int height, Image sprite) {
        this.dim = new Pair<>(width, height);
        this.sprite = sprite;
    }

    public void scale(int xScale, int yScale) {
        this.scaledDim = new Pair<>(this.dim.getFirst()*xScale, this.dim.getSecond()*yScale);
        this.scaledSprite = sprite.getScaledInstance(this.scaledDim.getFirst(), this.scaledDim.getSecond(), java.awt.Image.SCALE_SMOOTH);
    }

    public Image getScaledSprite() {
        return this.scaledSprite;
    }

    public Pair<Integer, Integer> getScaledDimension() {
        return new Pair<Integer, Integer> (this.scaledDim.getFirst(), this.scaledDim.getSecond());
    }

    public Position getApplicationPoint( Position pos ) {
        return new Position( pos.getX() - this.dim.getFirst()/2, pos.getY() - this.dim.getSecond()/2 );
    }
}
