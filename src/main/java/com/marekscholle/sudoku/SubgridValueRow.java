package com.marekscholle.sudoku;

import com.marekscholle.sudoku.Pos.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * If a value is possible in subgrid only in some row, then it is impossible
 * on the same row in other subgrids.
 */
public class SubgridValueRow implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger("SubgridValueRow");

    private final List<Box> subgrid;
    private final Value value;
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

        final Set<Row> rows = new HashSet<>();
        subgrid.forEach(b -> {
            if (b.isPossible(value)) {
                rows.add(b.getPos().row);
            }
        });
        if (rows.size() == 1) {
            board.rowBoxes(rows.iterator().next()).forEach(b -> {
                if (subgrid.stream().noneMatch(s -> b.getPos().equals(s.getPos()))) {
                    b.setImpossible(value);
                }
            });
        }
    }
}
