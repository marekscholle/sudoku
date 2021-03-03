package com.marekscholle.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Optional;

public class Solver {
    private static final Logger LOGGER = LoggerFactory.getLogger("Solver");

    /**
     * Recursive trying of solution.
     * It returns true if we have a solution.
     * It selects a box and tries its possible values. For each possible value,
     * it runs all the rules; if we reach a problem, exception is thrown and we
     * restore the state before setting and continue.
     * If we don't get a solution, we recursively try another box.
     */
    static boolean run(Board board) {
        // find a box with multiple possible values; select that has min possible values (>1)
        final Optional<Box> boxOptional = Pos.values().stream()
                .map(board::box)
                .filter(b -> b.possibleValueCount() > 1)
                .min(Comparator.comparingInt(Box::possibleValueCount));
        if (boxOptional.isEmpty()) {
            LOGGER.info("board is solved, terminating");
            return true;
        }
        final var box = boxOptional.get();
        LOGGER.info("guessing value, {}, from {}", box.getPos(), box.possibleValues());

        LOGGER.debug("snapping: {}", box.getPos());
        final var snapshot = board.snapshot();
        for (final var value : box.possibleValues()) {
            LOGGER.debug("trying: {}, {}", box.getPos(), value);
            try {
                box.setValue(value);
                if (run(board)) {
                    return true;
                }
            } catch (Exception e) {
                LOGGER.debug("restore: {}", box.getPos());
                board.restore(snapshot);
            }
        }
        throw new IllegalStateException("run did not solve the board");
    }
}
