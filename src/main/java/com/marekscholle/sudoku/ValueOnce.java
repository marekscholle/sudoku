package com.marekscholle.sudoku;

import com.marekscholle.sudoku.Pos.Col;
import com.marekscholle.sudoku.Pos.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ValueOnce implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger("ValueOnce");

    final Value value;
    final List<Box> boxes;

    protected ValueOnce(Value value, List<Box> boxes) {
        this.value = value;
        this.boxes = boxes;
        boxes.forEach(b -> b.addListener(this));
    }

    @Override
    public void onSetValue(Pos pos, Value value) {
        if (this.value.equals(value)) {
            assert boxes.stream().anyMatch(b -> b.getPos().equals(pos));
            LOGGER.debug("{}: set {}, {}", description(), pos, value);
            boxes
                    .stream()
                    .filter(b -> !b.getPos().equals(pos))
                    .forEach(b -> b.setImpossible(value));
        }
    }

    abstract String description();

    @Override
    public void onSetImpossible(Pos pos, Value value) {
        if (value.equals(this.value)) {
            assert boxes.stream().anyMatch(b -> b.getPos().equals(pos));
            LOGGER.debug("{}: set impossible {}, {}", description(), pos, value);
            final var possibleBoxes = boxes
                    .stream()
                    .filter(b -> b.isPossible(value))
                    .collect(Collectors.toList());
            if (possibleBoxes.size() == 1) {
                possibleBoxes.get(0).setValue(value);
            } else if (possibleBoxes.size() == 0) {
                throw new IllegalStateException(value + " has no possible box");
            }
        }
    }

    static Rule row(Board board, Row row, Value value) {
        return new ValueOnce(value, board.row(row)) {
            @Override
            public String description() {
                return String.format("%s once in %s", value, row);
            }
        };
    }

    static ValueOnce col(Board board, Col col, Value value) {
        return new ValueOnce(value, board.col(col)) {
            @Override
            public String description() {
                return String.format("%s once in col %s", value, col);
            }
        };
    }

    static ValueOnce subgrid(Board board, Pos topLeft, Value value) {
        return new ValueOnce(value, board.subgrid(topLeft)) {
            @Override
            public String description() {
                return String.format("%s once in subgrid %s", value, topLeft);
            }
        };
    }
}
