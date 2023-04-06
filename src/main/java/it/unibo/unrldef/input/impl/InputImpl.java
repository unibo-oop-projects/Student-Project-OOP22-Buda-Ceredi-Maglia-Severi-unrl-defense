package it.unibo.unrldef.input.impl;

import java.util.Objects;
import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.input.api.Input;

/**
 * Implementation of an input.
 * 
 * @author tommaso.severi2@studio.unibo.it
 */
public final class InputImpl implements Input {

    private InputType inputType;
    private Optional<Position> selectedPosition;
    private Optional<String> selectedName;

    /**
     * Builds a new input.
     * 
     * @param inputType        the input type
     * @param selectedPosition the selected
     * @param selectedName     the selected name
     */
    public InputImpl(final InputType inputType, final Position selectedPosition, final String selectedName) {
        this.inputType = Objects.requireNonNull(inputType);
        this.selectedPosition = Optional.ofNullable(selectedPosition);
        this.selectedName = Optional.ofNullable(selectedName);
    }

    /**
     * Builds a new input.
     * 
     * @param inputType the input type
     */
    public InputImpl(final InputType inputType) {
        this(inputType, null, null);
    }

    /**
     * Builds a new input.
     * 
     * @param inputType        the inputType type
     * @param selectedPosition the selected position
     */
    public InputImpl(final InputType inputType, final Position selectedPosition) {
        this(inputType, selectedPosition, null);
    }

    /**
     * Builds a new input.
     * 
     * @param inputType    the input type
     * @param selectedName the selected name
     */
    public InputImpl(final InputType inputType, final String selectedName) {
        this(inputType, null, selectedName);
    }

    @Override
    public InputType getInputType() {
        return this.inputType;
    }

    @Override
    public Optional<Position> getSelectedPosition() {
        return this.selectedPosition;
    }

    @Override
    public Optional<String> getSelectedName() {
        return this.selectedName;
    }
}
