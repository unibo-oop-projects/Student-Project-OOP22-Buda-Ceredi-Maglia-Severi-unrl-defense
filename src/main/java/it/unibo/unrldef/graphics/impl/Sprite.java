package it.unibo.unrldef.graphics.impl;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;

import java.awt.Image;

/**
 * the class that represents a sprite and its dimension.
 */
public class Sprite {
    private final Pair<Integer, Integer> dim;
    private Pair<Integer, Integer> scaledDim;
    private final Image sprite;
    private Image scaledSprite;

    /**
     * the constructor.
     * @param width the real width of the sprite
     * @param height the real height of the sprite
     * @param sprite the image of the sprite
     */
    public Sprite(final int width, final int height, final Image sprite) {
        this.dim = new Pair<>(width, height);
        this.sprite = sprite;
    }

    /**
     * Scales the sprite, given the scale factors.
     * @param xScale the scale factor for the x axis
     * @param yScale the scale factor for the y axis
     */
    public void scale(final double xScale, final double yScale) {
        this.scaledDim = new Pair<Integer, Integer>((int) (this.dim.getFirst() * xScale),
                (int) (this.dim.getSecond() * yScale));
        this.scaledSprite = sprite.getScaledInstance(this.scaledDim.getFirst(), this.scaledDim.getSecond(),
                java.awt.Image.SCALE_SMOOTH);
    }

    /**
     * 
     * @return the sprite in the right dimension
     */
    public Image getScaledSprite() {
        return this.scaledSprite;
    }

    /**
     * 
     * @return the scaled dimension of the sprite
     */
    public Pair<Integer, Integer> getScaledDimension() {
        return new Pair<Integer, Integer>(this.scaledDim.getFirst(), this.scaledDim.getSecond());
    }

    /**
     *
     * @param pos
     * @return the point in witch to render the sprite in order to have it centered
     */
    public Position getApplicationPoint(final Position pos) {
        return new Position(pos.getX() - this.dim.getFirst() / 2, pos.getY() - this.dim.getSecond() / 2);
    }
}
