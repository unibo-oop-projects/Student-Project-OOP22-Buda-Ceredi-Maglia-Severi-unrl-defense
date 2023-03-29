package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.model.api.Integrity;

/**
 * the integrity of a game entity in Unreal Defense.
 * 
 * @author francesco.buda3@studio.unibo.it
 */
public class IntegrityImpl implements Integrity {

    private int hearts;

    /**
     * the constructor.
     * 
     * @param value the starting number of hearts
     */
    public IntegrityImpl(final int value) {
        this.hearts = value;
    }

    @Override
    public int getHearts() {
        return this.hearts;
    }

    @Override
    public void damage(final int val) {
        int tmp = this.hearts - val;
        this.hearts = tmp > 0 ? tmp : 0;
    }

    @Override
    public Boolean isCompromised() {
        return this.hearts == 0;
    }
}
