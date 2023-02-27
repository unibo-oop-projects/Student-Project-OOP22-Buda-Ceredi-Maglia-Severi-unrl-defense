package it.unibo.unrldef.model.api;

/**
 * the integrity of a game entity
 * @author francesco.buda3@studio.unibo.it
 */
public interface Integrity {
    int getValue();
    void damage(int val);
}
