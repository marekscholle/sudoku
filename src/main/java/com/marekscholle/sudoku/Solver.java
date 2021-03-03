package com.marekscholle.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Solver {
    private static final Logger LOGGER = LoggerFactory.getLogger("Solver");

    static boolean guess(Board board) {
        // TODO by min possibleValues
        final Optional<Box> first = Pos.values().stream()
                .map(board::box)
                .filter(b -> b.possibleValues().size() > 1)
                .findFirst();
        if (first.isEmpty()) {
            return true;
        }
        final var box = first.get();

        LOGGER.info("snapping: {}", box.getPos());
        final var snap = board.snap();
        for (final var value : box.possibleValues()) {
            LOGGER.info("trying: {}, {}", box.getPos(), value);
            try {
                box.setValue(value);
                if (guess(board)) {
                    return true;
                }
            } catch (Exception e) {
                LOGGER.info("recover: {}", box.getPos());
                board.recover(snap);
            }
        }
        throw new IllegalStateException("guess did not solve the board");
    }
}
