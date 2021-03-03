package com.marekscholle.sudoku;

import com.marekscholle.sudoku.Pos.Col;
import com.marekscholle.sudoku.Pos.Row;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.marekscholle.sudoku.Const.SIZE;
import static com.marekscholle.sudoku.Const.SUBGRID_SIZE;

public class Board {
    final private Box[][] boxes;

    public Board() {
        boxes = new Box[SIZE][];
        for (int i = 0; i < SIZE; ++i) {
            final var row = new Box[SIZE];
            for (int j = 0; j < SIZE; ++j) {
                row[j] = new Box(Pos.of(Row.of(i), Col.of(j)));
            }
            boxes[i] = row;
        }
    }

    public Box box(Pos pos) {
        return boxes[pos.row.value][pos.col.value];
    }

    public List<Box> rowBoxes(Row row) {
        return Arrays.stream(boxes[row.value]).collect(Collectors.toList());
    }

    public List<Box> colBoxes(Col col) {
        return IntStream.range(0, SIZE)
                .mapToObj(i -> boxes[i][col.value])
                .collect(Collectors.toList());
    }

    public List<Box> subgrid(Pos topLeft) {
        var res = new ArrayList<Box>();
        for (int i = 0; i < SUBGRID_SIZE; ++i) {
            for (int j = 0; j < SUBGRID_SIZE; ++j) {
                res.add(box(topLeft.offset(i, j)));
            }
        }
        return res;
    }
}
