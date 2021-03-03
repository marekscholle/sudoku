package com.marekscholle.sudoku;

import com.marekscholle.sudoku.Const.Value;
import com.marekscholle.sudoku.Pos.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static com.marekscholle.sudoku.Const.SUBGRID_SIZE;

public class SubgridValueRow implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger("SubgridValueRow");

    private final List<Box> subgrid;
    private final Value value;
    final Row[] possibleRows = new Row[SUBGRID_SIZE];
    private final Board board;

    SubgridValueRow(Board board, List<Box> subgrid, Value value) {
        this.board = board;
        this.subgrid = subgrid;
        this.value = value;
        subgrid.forEach(b -> b.addListener(this));
    }

    @Override
    public void onSetValue(Pos pos, Value value) {
    }

    @Override
    public void onSetImpossible(Pos pos, Value value) {
        if (!this.value.equals(value)) {
            return;
        }

        // TODO
        Arrays.fill(possibleRows, null);
        subgrid.forEach(b -> {
            if (b.isPossible(value)) {
                possibleRows[b.getPos().row.value % SUBGRID_SIZE] = b.getPos().row;
            }
        });
        Row row = null;
        for (int i = 0; i < SUBGRID_SIZE; ++i) {
            if (row != null && possibleRows[i] != null) {
                return;
            }
            if (possibleRows[i] != null) {
                row = possibleRows[i];
            }
        }
        if (row == null) {
            return;
        }

        board.rowBoxes(row).forEach(b -> {
            if (subgrid.stream().noneMatch(s -> b.getPos().equals(s.getPos()))) {
                b.setImpossible(value);
            }
        });
    }
}
