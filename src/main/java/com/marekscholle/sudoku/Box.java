package com.marekscholle.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.marekscholle.sudoku.Const.SIZE;

public class Box {
    private static final Logger LOGGER = LoggerFactory.getLogger("Box");

    private final Pos pos;
    private final ArrayList<Rule> listeners = new ArrayList<>();

    private boolean[] possibleValues = new boolean[SIZE];
    private Optional<Value> value = Optional.empty();

    public Box(Pos pos) {
        this.pos = pos;
        Arrays.fill(possibleValues, true);
    }

    public Pos getPos() {
        return pos;
    }

    void addListener(Rule rule) {
        listeners.add(rule);
    }

    public void setImpossible(Value value) {
        if (this.value.isPresent() && this.value.get().equals(value)) {
            throw new IllegalStateException("value can't be set to impossible");
        }
        if (possibleValues[value.value]) {
            LOGGER.debug("set impossible: {}, {}", pos, value);
            possibleValues[value.value] = false;
            listeners.forEach(l -> l.onSetImpossible(pos, value));
        }
    }

    public boolean isPossible(Value value) {
        return possibleValues[value.value];
    }

    public List<Value> possibleValues() {
        return Value.values().stream().filter(this::isPossible).collect(Collectors.toList());
    }

    public void setValue(Value value) {
        if (this.value.isPresent()) {
            if (!this.value.get().equals(value)) {
                throw new IllegalStateException("setting different value");
            }
            return;
        }
        assert possibleValues[value.value];

        LOGGER.info("set value: {}, {}", pos, value);
        this.value = Optional.of(value);

        Value.values().stream()
                .filter(v -> !v.equals(value))
                .forEach(this::setImpossible);
        listeners.forEach(l -> l.onSetValue(pos, value));
    }

    public Snap snap() {
        return new Snap(
                Arrays.copyOf(possibleValues, possibleValues.length),
                this.value
        );
    }

    public void recover(Snap snap) {
        this.possibleValues = Arrays.copyOf(snap.possibleValues, snap.possibleValues.length);
        this.value = snap.value;
    }

    static class Snap {
        private final boolean[] possibleValues;
        private final Optional<Value> value;

        Snap(boolean[] possibleValues, Optional<Value> value) {
            this.possibleValues = possibleValues;
            this.value = value;
        }
    }
}
