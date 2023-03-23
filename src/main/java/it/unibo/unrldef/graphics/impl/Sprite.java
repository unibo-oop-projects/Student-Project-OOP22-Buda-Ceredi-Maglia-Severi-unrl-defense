package it.unibo.unrldef.graphics.impl;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;

import java.awt.Image;


public class Sprite {
    private final Pair<Integer, Integer> dim;
    private Pair<Integer, Integer> scaledDim;
    private final Image sprite;
    private Image scaledSprite;

    public Sprite(int length, int eight, Image sprite) {
        this.dim = new Pair<>(length, eight);
        this.sprite = sprite;
    }

    public void scale(int scaleFactor) {
        this.scaledDim = new Pair<>(this.dim.getFirst()*scaleFactor, this.dim.getSecond()*scaleFactor);
        this.scaledSprite = sprite.getScaledInstance(this.scaledDim.getFirst(), this.scaledDim.getSecond(), java.awt.Image.SCALE_SMOOTH);
    }

    public Image getScaledSprite() {
        return this.scaledSprite;
    }

    public Position getApplicationPoint( Position pos ) {
        return new Position( pos.getX() - this.scaledDim.getFirst()/2, pos.getY() - this.scaledDim.getSecond()/2 );
    }
}
