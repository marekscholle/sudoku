package com.marekscholle.sudoku;

import com.marekscholle.sudoku.Pos.Col;
import com.marekscholle.sudoku.Pos.Row;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;

import static com.marekscholle.sudoku.Const.SIZE;
import static org.junit.Assert.assertEquals;

public class AppTest {
    private static final Logger LOGGER = LoggerFactory.getLogger("AppTest");

    private static void checkUnique(List<Box> boxes) {
        assert boxes.size() == SIZE;
        final var set = new HashSet<Value>();
        boxes.forEach(b -> {
            final var possibleValues = b.possibleValues();
            assertEquals(1, possibleValues.size());
            set.add(possibleValues.get(0));
        });
        assertEquals(SIZE, set.size());
    }

    private static void checkResult(List<Input.Elem> input,  Board board) {
        Row.values().forEach(row -> checkUnique(board.row(row)));
        Col.values().forEach(col -> checkUnique(board.col(col)));
        Row.values().forEach(row -> Col.values().forEach(col -> checkUnique(board.subgrid(Pos.of(row, col)))));
        input.forEach(in -> assertEquals(in.value, board.box(in.pos).possibleValues().get(0)));
    }

    public void test(Board board, String exercise, boolean guess) {
        var input = Input.read(exercise);
        input.forEach(in -> board.box(in.pos).setValue(in.value));
        if (guess) {
            Solver.guess(board);
        }
        checkResult(input, board);
        LOGGER.info("Result:\n{}", Visualizer.draw(board));
    }

    @Test
    public void easy() {
        var board = new Board();
        Rules.all(board);
        test(board, " 138  4 5| 246 5   | 87   93 |49 3 6   |  1   5  |   7 1 93| 69   74 |   2 768 |1 2  835 ", false);
    }

    @Test
    public void difficultAll() {
        var board = new Board();
        Rules.all(board);
        test(board, "  2    41|    82 7 |    4   9|2   793  | 1     8 |  681   4|1   9    | 6 43    |85    4  ", false);
    }

    @Test
    public void difficultGuess() {
        var board = new Board();
        Rules.valueOnce(board);
        Rules.singleValue(board);
        test(board, "  2    41|    82 7 |    4   9|2   793  | 1     8 |  681   4|1   9    | 6 43    |85    4  ", true);
    }

    @Test
    public void empty() {
        var board = new Board();
        Rules.all(board);
        Solver.guess(board);
        LOGGER.info("empty solution (one of many):\n{}", Visualizer.draw(board));
    }
}
