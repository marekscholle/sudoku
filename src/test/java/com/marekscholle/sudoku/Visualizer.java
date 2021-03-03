package com.marekscholle.sudoku;

import com.marekscholle.sudoku.Pos.Col;
import com.marekscholle.sudoku.Pos.Row;

import static com.marekscholle.sudoku.Const.SUBGRID_SIZE;

public class Visualizer {
    private static void drawBox(Box box, StringBuilder b) {
        Value.values().forEach(v -> {
            if (box.isPossible(v)) {
                b.append(v.value + 1);
            } else {
                b.append(' ');
            }
        });
    }

    private static final String hline = ("═".repeat(91)) + '\n';

    static String draw(Board board) {
        var b = new StringBuilder();
        b.append(hline);
        Row.values().forEach(row -> {
            b.append('║');
            Col.values().forEach(col -> {
                drawBox(board.box(Pos.of(row, col)), b);
                if (col.value % SUBGRID_SIZE == SUBGRID_SIZE - 1) {
                    b.append('║');
                } else {
                    b.append('|');
                }
            });
            b.append('\n');
            if (row.value % SUBGRID_SIZE == SUBGRID_SIZE - 1) {
                b.append(hline);
            }
        });
        return b.toString();
    }
}
