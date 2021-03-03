package com.marekscholle.sudoku;

import java.util.Objects;
import java.util.stream.IntStream;

import static com.marekscholle.sudoku.Const.SIZE;

public class Pos {
    final Row row;
    final Col col;

    private static final Pos[][] VALUES =
            IntStream.range(0, SIZE)
                    .mapToObj(i ->
                            IntStream.range(0, SIZE)
                                    .mapToObj(j -> new Pos(Row.of(i), Col.of(j)))
                                    .toArray(Pos[]::new)
                    )
                    .toArray(Pos[][]::new);

    private Pos(Row row, Col col) {
        this.row = row;
        this.col = col;
    }

    static Pos of(Row row, Col col) {
        return VALUES[row.value][col.value];
    }

    static Pos of(int i, int j) {
        return new Pos(Row.of(i), Col.of(j));
    }

    Pos offset(int i, int j) {
        return of(row.offset(i), col.offset(j));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pos pos = (Pos) o;
        return Objects.equals(row, pos.row) && Objects.equals(col, pos.col);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "Pos(" + row + "," + col + ')';
    }

    static class Row {
        final int value;

        private static final Row[] VALUES =
                IntStream.range(0, SIZE).mapToObj(Row::new).toArray(Row[]::new);

        private Row(int value) {
            this.value = value;
        }

        static Row of(int value) {
            return new Row(value);
        }

        Row offset(int i) {
            return of(value + i);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Row row = (Row) o;
            return value == row.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Row(" + value + ')';
        }
    }

    static class Col {
        final int value;

        private static final Col[] VALUES =
                IntStream.range(0, SIZE).mapToObj(Col::new).toArray(Col[]::new);

        private Col(int value) {
            this.value = value;
        }

        static Col of(int value) {
            return VALUES[value];
        }

        Col offset(int j) {
            return of(value + j);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Col col = (Col) o;
            return value == col.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Col(" + value + ')';
        }
    }
}
