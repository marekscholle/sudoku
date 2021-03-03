package com.marekscholle.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        assert this.box.getPos().equals(pos);
        final var possibleValues = this.box.possibleValues();
        if (possibleValues.size() == 1) {
            LOGGER.info("found the only possible value: {}, {}", box.getPos(), possibleValues.get(0));
            box.setValue(possibleValues.get(0));
        }
    }
}
