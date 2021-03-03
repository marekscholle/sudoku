package com.marekscholle.sudoku;

import com.marekscholle.sudoku.Const.Value;
import com.marekscholle.sudoku.Coords.Col;
import com.marekscholle.sudoku.Coords.Pos;
import com.marekscholle.sudoku.Coords.Row;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;

import static com.marekscholle.sudoku.Const.SIZE;

public class AppTest {
    private static final Logger LOGGER = LoggerFactory.getLogger("AppTest");

    static class Input {
        final Pos pos;
        final Value value;

        Input(Pos pos, Value value) {
            this.pos = pos;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Input{" +
                    "pos=" + pos +
                    ", value=" + value +
                    '}';
        }
    }

    private ArrayList<Input> read(String input) {
        var res = new ArrayList<Input>();
        var rows = input.split("\\|");
        assert rows.length == SIZE;
        assert Arrays.stream(rows).allMatch(r -> r.length() == SIZE);
        for (int i = 0; i < SIZE; ++i) {
            var r= rows[i];
            for (int j = 0; j < SIZE; ++j) {
                if (r.charAt(j) != ' ') {
                    var v = Character.getNumericValue(r.charAt(j));
                    res.add(new Input(Pos.of(Row.of(i), Col.of(j)), Value.of(v - 1)));
                }
            }
        }
        return res;
    }

    @Test
    public void test() {
        var board = new Board();
        LOGGER.info("\n{}", Visualizer.draw(board));
        Rules.valueOnce(board);
        Rules.singleValue(board);
        Rules.subgridValueRow(board);

        //final var input = " 138  4 5| 246 5   | 87   93 |49 3 6   |  1   5  |   7 1 93| 69   74 |   2 768 |1 2  835 ";
        final var input = "  2    41|    82 7 |    4   9|2   793  | 1     8 |  681   4|1   9    | 6 43    |85    4  ";
        var inputs = read(input);

        inputs.forEach(
            in -> {
                LOGGER.info("set {} to {}", in.pos, in.value);
                board.box(in.pos).setValue(in.value);
            }
        );

        LOGGER.info("\n{}", Visualizer.draw(board));
    }
}