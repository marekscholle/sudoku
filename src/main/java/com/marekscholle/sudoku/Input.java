package com.marekscholle.sudoku;

import com.marekscholle.sudoku.Pos.Col;
import com.marekscholle.sudoku.Pos.Row;

import java.util.ArrayList;
import java.util.Arrays;

import static com.marekscholle.sudoku.Const.SIZE;

public class Input {
    public static class Elem {
        final Pos pos;
        final Value value;

        Elem(Pos pos, Value value) {
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

    /**
     * Reads given values to as list.
     * The format of input is {@code <row>|<row>|...|<row>}
     * where {@code <row>} a sequence of digits or space of length 9.
     * Example: {@code 123   789} is a row with given 1,2,3 in first
     * subgrid and 7,8,9 in the last subgrid.
     */
    public static ArrayList<Elem> read(String input) {
        var res = new ArrayList<Elem>();
        final var rows = input.split("\\|");
        assert rows.length == SIZE;
        assert Arrays.stream(rows).allMatch(r -> r.length() == SIZE);
        Row.values().forEach(row -> {
            final var r = rows[row.value];
            Col.values().forEach(col -> {
                if (r.charAt(col.value) != ' ') {
                    var v = Character.getNumericValue(r.charAt(col.value));
                    res.add(new Elem(Pos.of(row, col), Value.of(v - 1)));
                }
            });
        });
        return res;
    }

}
