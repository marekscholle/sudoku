package com.marekscholle.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger("App");

    public static void main(String[] args) {
        var input = Input.read(args[0]);

        final var board = new Board();
        Rules.all(board);

        input.forEach(
                in -> {
                    LOGGER.info("set {} to {}", in.pos, in.value);
                    board.box(in.pos).setValue(in.value);
                    LOGGER.info("Intermediate result:\n{}", Visualizer.draw(board));
                }
        );

        LOGGER.info("Result:\n{}", Visualizer.draw(board));
    }
}
