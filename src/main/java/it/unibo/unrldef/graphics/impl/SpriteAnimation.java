package it.unibo.unrldef.graphics.impl;

import java.util.Optional;

import it.unibo.unrldef.model.api.Entity;

/**
 * Handles the attck animation of a game entity
 * @author tommaso.severi2@studio.unibo.it
 */
public class SpriteAnimation {
    
    private final long DEFAULT_TIME = -1;

    private long startTime;
    private long timePassed;
    private final long animationLength;
    private Optional<Entity> target;

    /**
     * Builds a new animation handler
     * @param animationLength the length of the animation
     */
    public SpriteAnimation(final long animationLength) {
        this.animationLength = animationLength;
        this.resetAnimation();
    }

    /**
     * Sets up the start of the animation
     * @param startTime the current time in which the animation is starting
     * @param target the target towards the animation is referred to 
     */
    public void startAnimation(final long startTime, final Entity target) {
        this.startTime = startTime;
        this.target = Optional.of(target);
        this.updateTimePassed();
    }

    /**
     * Updates the time that has passed since the starting time
     */
    public void updateTimePassed() {
        this.timePassed = System.currentTimeMillis() - this.startTime;
    }

    /**
     * @return the time passed since the starting time
     */
    public long getTimePassed() {
        return this.timePassed != DEFAULT_TIME ? this.timePassed : 0;
    }

    /**
     * Checks if the animation is still running
     * @return true if it is, false otherwise
     */
    public boolean isAnimationRunning() {
        return this.timePassed < this.animationLength && this.timePassed != DEFAULT_TIME;
    }

    /**
     * @return The target of the animation
     */
    public Entity getTarget() {
        return this.target.get();
    }

    /**
     * Resets the animation to iss deafult state
     */
    public void resetAnimation() {
        this.startTime = DEFAULT_TIME;
        this.timePassed = DEFAULT_TIME;
        this.target = Optional.empty();
    }
}
