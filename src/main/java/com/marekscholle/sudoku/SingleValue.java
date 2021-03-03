package com.marekscholle.sudoku;

import com.marekscholle.sudoku.Const.Value;
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
        if (this.box.getPos().equals(pos)) {
            if (!possibleValues[value.intValue()]) {
                throw new IllegalStateException();
            }
            Arrays.fill(possibleValues, false);
            possibleValues[value.intValue()] = true;
            possibleCount = 1;
        }
    }

    @Override
    public void onSetImpossible(Pos pos, Value value) {
        if (this.box.getPos().equals(pos)) {
            if (possibleValues[value.intValue()]) {
                possibleValues[value.intValue()] = false;
                possibleCount -= 1;
                if (possibleCount == 1) {
                    for (int i = 0; i < SIZE; ++i) {
                        if (possibleValues[i]) {
                            LOGGER.debug("only possible value for {} is {}", box.getPos(), Value.of(i));
                            box.setValue(Value.of(i));
                        }
                    }
                }
            }
        }
    }
}
