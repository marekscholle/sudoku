package com.marekscholle.sudoku;

import com.marekscholle.sudoku.Pos.Col;
import com.marekscholle.sudoku.Pos.Row;

import static com.marekscholle.sudoku.Const.SIZE;
import static com.marekscholle.sudoku.Const.SUBGRID_SIZE;

public class Visualizer {
    private static void drawBox(Box box, StringBuilder b) {
        for (int i = 0; i < SIZE; ++i) {
            if (box.isPossible(Value.of(i))) {
                b.append(i + 1);
            } else {
                b.append(' ');
            }
        }
    }

    private static final String hline = ("═".repeat(91)) + '\n';

    static String draw(Board board) {
        var b = new StringBuilder();
        b.append(hline);
        for (int i = 0; i < SIZE; ++i) {
            b.append('║');
            for (int j = 0; j < SIZE; ++j) {
                drawBox(board.box(Pos.of(Row.of(i), Col.of(j))), b);
                if (j % SUBGRID_SIZE == SUBGRID_SIZE - 1) {
                    b.append('║');
                } else {
                    b.append('|');
                }
            }
            b.append('\n');
            if (i % SUBGRID_SIZE == SUBGRID_SIZE - 1) {
                b.append(hline);
            }
        }
        return b.toString();
    }
}
