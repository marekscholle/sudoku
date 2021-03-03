package com.marekscholle.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static com.marekscholle.sudoku.Const.SIZE;

public class SingleValue implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger("SingleValue");

    private final Box box;
    private final boolean[] possibleValues;
    private int possibleCount;

    public SingleValue(Box box) {
        this.box = box;
        possibleValues = new boolean[SIZE];
        Arrays.fill(possibleValues, true);
        possibleCount = SIZE;
        box.addListener(this);
    }

    @Override
    public void onSetValue(Pos pos, Value value) {
        assert this.box.getPos().equals(pos);
        if (!possibleValues[value.value]) {
            throw new IllegalStateException(value + " is impossible");
        }
        Arrays.fill(possibleValues, false);
        possibleValues[value.value] = true;
        possibleCount = 1;
    }

    @Override
    public void onSetImpossible(Pos pos, Value value) {
        assert this.box.getPos().equals(pos);
        if (possibleValues[value.value]) {
            possibleValues[value.value] = false;
            possibleCount -= 1;
            if (possibleCount == 1) {
                Value.values().stream()
                        .filter(v -> possibleValues[v.value])
                        .forEach(v -> {
                            LOGGER.debug("the only possible value for {} is {}", box.getPos(), v);
                            box.setValue(v);
                        });
            }
        }
    }
}
