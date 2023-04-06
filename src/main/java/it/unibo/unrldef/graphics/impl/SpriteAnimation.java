package it.unibo.unrldef.graphics.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;

/**
 * Handles the attack animation of a game entity.
 * 
 * @author tommaso.severi2@studio.unibo.it
 */
public class SpriteAnimation {

    private static final long DEFAULT_TIME = -1;

    private long startTime;
    private long timePassed;
    private final long animationLength;
    private Optional<Enemy> target;
    private Optional<Position> startPosition;

    /**
     * Builds a new animation handler.
     * 
     * @param animationLength the length of the animation
     */
    public SpriteAnimation(final long animationLength) {
        this.animationLength = animationLength;
        this.resetAnimation();
    }

    /**
     * Sets up the start of the animation.
     * 
     * @param startTime the current time in which the animation is starting
     * @param target    the target towards the animation is referred to
     */
    public void startAnimation(final long startTime, final Enemy target) {
        this.startTime = startTime;
        this.target = Optional.of(target);
        this.updateTimePassed();
        this.setStartTargetPosition();
    }

    /**
     * Updates the time that has passed since the starting time.
     */
    public void updateTimePassed() {
        this.timePassed = System.currentTimeMillis() - this.startTime;
    }

    /**
     * @return the time passed since the starting time.
     */
    public long getTimePassed() {
        return this.timePassed != DEFAULT_TIME ? this.timePassed : 0;
    }

    /**
     * Checks if the animation is still running.
     * 
     * @return true if it is running, false otherwise
     */
    public boolean isAnimationRunning() {
        return this.timePassed < this.animationLength && this.timePassed != DEFAULT_TIME;
    }

    /**
     * @return The target of the animation
     */
    public Enemy getTarget() {
        return this.target.get();
    }

    /**
     * Resets the animation to its deafult state.
     */
    public final void resetAnimation() {
        this.startTime = DEFAULT_TIME;
        this.timePassed = DEFAULT_TIME;
        this.target = Optional.empty();
        this.startPosition = Optional.empty();
    }

    /**
     * Sets the starting position of the animation.
     */
    private void setStartTargetPosition() {
        this.startPosition = this.target.get().getPosition();
    }

    /**
     * @return the starting position of the animation
     */
    public Position getStartTargetPosition() {
        return this.startPosition.get();
    }
}
