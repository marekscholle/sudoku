package com.marekscholle.sudoku;

import com.marekscholle.sudoku.Pos.Col;
import com.marekscholle.sudoku.Pos.Row;

import java.util.ArrayList;
import java.util.Arrays;

import static com.marekscholle.sudoku.Const.SIZE;

public class Input {
    public final ArrayList<Elem> elems;

    public Input(ArrayList<Elem> elems) {
        this.elems = elems;
    }

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
     * Interprets given string as sudoku input.
     * The format of input is {@code <row>|<row>|...|<row>}
     * where {@code <row>} a sequence of digits and/or spaces of length 9.
     * Example: {@code 123   789} is a row with given 1,2,3 in first
     * subgrid and 7,8,9 in the last subgrid.
     */
    public static Input read(String input) {
        var elems = new ArrayList<Elem>();
        final var rows = input.split("\\|");
        if (rows.length != SIZE) {
            throw new IllegalArgumentException("expected " + SIZE + " rows");
        }
        if (Arrays.stream(rows).anyMatch(r -> r.length() != SIZE)) {
            throw new IllegalArgumentException("expected every row to be of length " + SIZE);
        }
        Row.values().forEach(row -> {
            final var r = rows[row.value];
            Col.values().forEach(col -> {
                if (r.charAt(col.value) != ' ') {
                    var v = Character.getNumericValue(r.charAt(col.value));
                    elems.add(new Elem(Pos.of(row, col), Value.of(v - 1)));
                }
            });
        });
        return new Input(elems);
    }
}
