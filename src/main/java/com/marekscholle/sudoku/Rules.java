package com.marekscholle.sudoku;

import com.marekscholle.sudoku.Const.Value;
import com.marekscholle.sudoku.Coords.Col;
import com.marekscholle.sudoku.Coords.Pos;
import com.marekscholle.sudoku.Coords.Row;

import java.util.ArrayList;
import java.util.List;

import static com.marekscholle.sudoku.Const.SIZE;
import static com.marekscholle.sudoku.Const.SUBGRID_SIZE;

public class Rules {
    @SuppressWarnings("UnusedReturnValue")
    static List<Rule> valueOnce(Board board) {
        final var rules = new ArrayList<Rule>();
        for (int value = 0; value < SIZE; ++value) {
            for (int i = 0; i < SIZE; ++i) {
                rules.add(ValueOnce.row(board, Row.of(i), Value.of(value)));
            }
            for (int j = 0; j < SIZE; ++j) {
                rules.add(ValueOnce.col(board, Col.of(j), Value.of(value)));
            }
            for (int i = 0; i < SIZE; i += SUBGRID_SIZE) {
                for (int j = 0; j < SIZE; j += SUBGRID_SIZE) {
                    rules.add(ValueOnce.subgrid(board, Pos.of(Row.of(i), Col.of(j)), Value.of(value)));
                }
            }
        }
        return rules;
    }

    @SuppressWarnings("UnusedReturnValue")
    static List<Rule> singleValue(Board board) {
        final var rules = new ArrayList<Rule>();
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                rules.add(new SingleValue(board.box(Pos.of(Row.of(i), Col.of(j)))));
            }
        }
        return rules;
    }
}
