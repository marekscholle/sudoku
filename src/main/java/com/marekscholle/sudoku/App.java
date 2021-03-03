package com.marekscholle.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger("App");

    private static void solve(Input input) {
        final var board = new Board();
        // You can select rules which are checked.
        //Rules.valueOnce(board);
        //Rules.singleValue(board);
        //Rules.subgridValue(board);
        Rules.all(board);

        input.elems.forEach(e -> {
            LOGGER.info("set {} to {}", e.pos, e.value);
            board.box(e.pos).setValue(e.value);
            LOGGER.info("Intermediate result:\n{}", Visualizer.draw(board));
        });
        LOGGER.info("Intermediate result after set all input values:\n{}", Visualizer.draw(board));

        Solver.guess(board);
        LOGGER.info("Result:\n{}", Visualizer.draw(board));
    }

    public static void main(String[] args) {
        Input input = null;
        try {
            input = Input.read(args[0]);
        } catch (Exception e) {
            System.out.println("Can't read input: " + args[0]);
            e.printStackTrace();
            System.exit(1);
        }
        try {
            solve(input);
        } catch (Exception e) {
            System.out.println("Error while solving, perhaps there is no solution for it");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
