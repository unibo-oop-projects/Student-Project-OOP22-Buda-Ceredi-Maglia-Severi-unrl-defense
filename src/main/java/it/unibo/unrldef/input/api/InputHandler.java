package it.unibo.unrldef.input.api;

import java.util.List;

/**
 * Interface of the input handler of the player.
 * 
 * @author tommaso.severi2@studio.unibo.it
 */
public interface InputHandler {
    /**
     * Adds a new input to the queue.
     * 
     * @param input the input to add
     */
    void addInput(Input input);

    /**
     * Returns the list of inputs resetting them.
     * 
     * @return the list of inputs
     */
    List<Input> getInputs();
}
