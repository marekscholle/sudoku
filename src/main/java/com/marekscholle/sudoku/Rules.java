package com.marekscholle.sudoku;

import com.marekscholle.sudoku.Pos.Col;
import com.marekscholle.sudoku.Pos.Row;

import static com.marekscholle.sudoku.Const.SIZE;
import static com.marekscholle.sudoku.Const.SUBGRID_SIZE;

public class Rules {
    static void valueOnce(Board board) {
        Value.values().forEach(value -> {
            Row.values().forEach(row -> ValueOnce.row(board, row, value));
            Col.values().forEach(col -> ValueOnce.col(board, col, value));
            for (int i = 0; i < SIZE; i += SUBGRID_SIZE) {
                for (int j = 0; j < SIZE; j += SUBGRID_SIZE) {
                    ValueOnce.subgrid(board, Pos.of(i, j), value);
                }
            }
        });
    }

    static void singleValue(Board board) {
        Pos.values().forEach(pos -> new SingleValue(board.box(pos)));
    }

    static void subgridValue(Board board) {
        for (int i = 0; i < SIZE; i += SUBGRID_SIZE) {
            for (int j = 0; j < SIZE; j += SUBGRID_SIZE) {
                final var pos = Pos.of(i, j);
                Value.values().forEach(v -> {
                    new SubgridValue.Row(board, board.subgrid(pos), v);
                    new SubgridValue.Col(board, board.subgrid(pos), v);
                });
            }
        }
    }

    static void all(Board board) {
        valueOnce(board);
        singleValue(board);
        subgridValue(board);
    }
}
