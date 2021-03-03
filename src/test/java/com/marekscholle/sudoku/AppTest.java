package com.marekscholle.sudoku;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppTest {
    private static final Logger LOGGER = LoggerFactory.getLogger("AppTest");

    @Test
    public void easy() {
        var board = new Board();
        LOGGER.info("\n{}", Visualizer.draw(board));
        Rules.all(board);
        final var input = " 138  4 5| 246 5   | 87   93 |49 3 6   |  1   5  |   7 1 93| 69   74 |   2 768 |1 2  835 ";
        var inputs = Input.read(input);

        inputs.forEach(
                in -> {
                    LOGGER.info("set {} to {}", in.pos, in.value);
                    board.box(in.pos).setValue(in.value);
                }
        );

        LOGGER.info("\n{}", Visualizer.draw(board));
    }

    @Test
    public void difficult() {
        var board = new Board();
        LOGGER.info("\n{}", Visualizer.draw(board));
        Rules.all(board);
        final var input = "  2    41|    82 7 |    4   9|2   793  | 1     8 |  681   4|1   9    | 6 43    |85    4  ";
        var inputs = Input.read(input);

        inputs.forEach(
                in -> {
                    LOGGER.info("set {} to {}", in.pos, in.value);
                    board.box(in.pos).setValue(in.value);
                }
        );

        LOGGER.info("\n{}", Visualizer.draw(board));
    }
}
