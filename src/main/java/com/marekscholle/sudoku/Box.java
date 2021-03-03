package com.marekscholle.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;

import static com.marekscholle.sudoku.Const.SIZE;

public class Box {
    private static final Logger LOGGER = LoggerFactory.getLogger("Box");

    private final Pos pos;
    private final boolean[] possibleValues;
    private boolean isSet = false;
    private final ArrayList<Rule> listeners = new ArrayList<>();

    public Box(Pos pos) {
        this.pos = pos;
        possibleValues = new boolean[SIZE];
        Arrays.fill(possibleValues, true);
    }

    public Pos getPos() {
        return pos;
    }

    void addListener(Rule rule) {
        listeners.add(rule);
    }

    public void setImpossible(Value value) {
        if (possibleValues[value.value]) {
            LOGGER.debug("setImpossible {} {}", pos, value);
            possibleValues[value.value] = false;
            listeners.forEach(l -> l.onSetImpossible(pos, value));
        }
    }

    public boolean isPossible(Value value) {
        return possibleValues[value.value];
    }

    public void setValue(Value value) {
        if (isSet) {
            if (!possibleValues[value.value]) {
                throw new IllegalStateException("setting to different value");
            }
        } else if (!possibleValues[value.value]) {
            throw new IllegalStateException("value not possible");
        } else {
            LOGGER.info("set value {} to {}", pos, value);
            for (int i = 0; i < SIZE; ++i) {
                if (i != value.value) {
                    setImpossible(Value.of(i));
                }
            }
            isSet = true;
            listeners.forEach(l -> l.onSetValue(pos, value));
        }
    }
}
