package com.marekscholle.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Optional;

public class Solver {
    private static final Logger LOGGER = LoggerFactory.getLogger("Solver");

    static boolean guess(Board board) {
        // TODO by min possibleValues
        final Optional<Box> first = Pos.values().stream()
                .map(board::box)
                .filter(b -> b.possibleValues().size() > 1)
                .min(Comparator.comparingInt(b -> b.possibleValues().size()));
        if (first.isEmpty()) {
            LOGGER.info("board is solved, terminating guess");
            return true;
        }

        final var box = first.get();
        LOGGER.info("guessing value for {} from {}", box.getPos(), box.possibleValues());

        LOGGER.debug("snapping: {}", box.getPos());
        final var snap = board.snap();
        for (final var value : box.possibleValues()) {
            LOGGER.debug("trying: {}, {}", box.getPos(), value);
            try {
                box.setValue(value);
                if (guess(board)) {
                    return true;
                }
            } catch (Exception e) {
                LOGGER.debug("recover: {}", box.getPos());
                board.recover(snap);
            }
        }
        throw new IllegalStateException("guess did not solve the board");
    }
}
