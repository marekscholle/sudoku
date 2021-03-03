package com.marekscholle.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rule that a box may have only one filled value.
 * It triggers {@link Box#setValue} if it detects that there remains
 * only single possible value.
 */
public class SingleValue implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger("SingleValue");

    private final Box box;

    public SingleValue(Box box) {
        this.box = box;
        box.addListener(this);
    }

    @Override
    public void onSetValue(Pos pos, Value value) {
    }

    @Override
    public void onSetImpossible(Pos pos, Value value) {
        assert box.getPos().equals(pos);
        if (box.possibleValueCount() == 1) {
            final var possibleValues = this.box.possibleValues();
            LOGGER.info("found the only possible value: {}, {}", box.getPos(), possibleValues.get(0));
            box.setValue(possibleValues.get(0));
        }
    }
}
