package it.unibo.unrldef.input.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.input.api.InputHandler;

/**
 * Implementation of the the player input handler.
 * 
 * @author tommaso.severi2@studio.unibo.it
 */
public final class InputHandlerImpl implements InputHandler {

    private final List<Input> inputs;

    /**
     * Builds a new input handler.
     */
    public InputHandlerImpl() {
        this.inputs = new ArrayList<>();
    }

    @Override
    public void addInput(final Input input) {
        this.inputs.add(Objects.requireNonNull(input));
    }

    @Override
    public List<Input> getInputs() {
        final List<Input> returnInputs = new ArrayList<>(this.inputs);
        this.inputs.clear();
        return returnInputs;
    }

}
