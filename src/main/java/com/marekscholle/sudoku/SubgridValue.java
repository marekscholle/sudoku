package com.marekscholle.sudoku;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * If a value is possible in subgrid only in some row/col, then it is impossible
 * on the same row/col in other subgrids.
 */
abstract public class SubgridValue implements Rule {
    protected final List<Box> subgrid;
    protected final Value value;
    protected final Board board;
    private boolean done = false;

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
        if (done || !this.value.equals(value)) {
            return;
        }
        done = _onSetImpossible(pos, value);
    }

    // Implementations for Row and Col are copy-pasted, but it is not worth to generalize.
    protected abstract boolean _onSetImpossible(Pos pos, Value value);

    static class Row extends SubgridValue {
        Row(Board board, List<Box> subgrid, Value value) {
            super(board, subgrid, value);
        }

        @Override
        protected boolean _onSetImpossible(Pos pos, Value value) {
            final Set<Pos.Row> rows = new HashSet<>();
            subgrid.forEach(b -> {
                if (b.isPossible(value)) {
                    rows.add(b.getPos().row);
                }
            });
            if (rows.size() != 1) {
                return false;
            }
            board.rowBoxes(rows.iterator().next()).forEach(b -> {
                if (subgrid.stream().noneMatch(s -> b.getPos().equals(s.getPos()))) {
                    b.setImpossible(value);
                }
            });
            return true;
        }
    }

    static class Col extends SubgridValue {
        Col(Board board, List<Box> subgrid, Value value) {
            super(board, subgrid, value);
        }

        @Override
        protected boolean _onSetImpossible(Pos pos, Value value) {
            final Set<Pos.Col> rows = new HashSet<>();
            subgrid.forEach(b -> {
                if (b.isPossible(value)) {
                    rows.add(b.getPos().col);
                }
            });
            if (rows.size() != 1) {
                return false;
            }
            board.colBoxes(rows.iterator().next()).forEach(b -> {
                if (subgrid.stream().noneMatch(s -> b.getPos().equals(s.getPos()))) {
                    b.setImpossible(value);
                }
            });
            return true;
        }
    }
}
