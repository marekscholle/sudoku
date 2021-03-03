package com.marekscholle.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * If a value is possible only in some row/col in subgrid, then it is impossible
 * on the same row/col in other subgrids.
 * This is a helper rule which may advance in some cases where more
 * primitive rules won't make any progress.
 * Registers self as listener for subgrid boxes.
 */
abstract public class SubgridValue implements Rule {
    private static final Logger LOGGER = LoggerFactory.getLogger("SubgridValue");

    protected final List<Box> subgrid;
    protected final Value value;
    protected final Board board;

    SubgridValue(Board board, List<Box> subgrid, Value value) {
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
        _onSetImpossible(pos, value);
    }

    // Implementations for Row and Col are copy-pasted, but it is not worth to generalize.
    protected abstract void _onSetImpossible(Pos pos, Value value);

    static class Row extends SubgridValue {
        Row(Board board, List<Box> subgrid, Value value) {
            super(board, subgrid, value);
        }

        @Override
        protected void _onSetImpossible(Pos pos, Value value) {
            final Set<Pos.Row> rows = new HashSet<>();
            subgrid.forEach(b -> {
                if (b.isPossible(value)) {
                    rows.add(b.getPos().row);
                }
            });
            if (rows.size() != 1) {
                return;
            }
            final var row = rows.iterator().next();
            LOGGER.info("found the row for {} at subgrid {}: {}", value, pos, row);
            board.row(row).forEach(b -> {
                if (subgrid.stream().noneMatch(s -> b.getPos().equals(s.getPos()))) {
                    b.setImpossible(value);
                }
            });
        }
    }

    static class Col extends SubgridValue {
        Col(Board board, List<Box> subgrid, Value value) {
            super(board, subgrid, value);
        }

        @Override
        protected void _onSetImpossible(Pos pos, Value value) {
            final Set<Pos.Col> cols = new HashSet<>();
            subgrid.forEach(b -> {
                if (b.isPossible(value)) {
                    cols.add(b.getPos().col);
                }
            });
            if (cols.size() != 1) {
                return;
            }
            final var col = cols.iterator().next();
            LOGGER.info("found the col for {} at subgrid {}: {}", value, pos, col);
            board.col(cols.iterator().next()).forEach(b -> {
                if (subgrid.stream().noneMatch(s -> b.getPos().equals(s.getPos()))) {
                    b.setImpossible(value);
                }
            });
        }
    }
}
