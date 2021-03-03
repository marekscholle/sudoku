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
    final private Box[][] boxes =
            Row.values().stream()
                    .map(row ->
                            Col.values().stream()
                                    .map(col -> new Box(Pos.of(row, col)))
                                    .toArray(Box[]::new)
                    )
                    .toArray(Box[][]::new);

    public Box box(Pos pos) {
        return boxes[pos.row.value][pos.col.value];
    }

    public List<Box> row(Row row) {
        return Arrays.stream(boxes[row.value]).collect(Collectors.toList());
    }

    public List<Box> col(Col col) {
        return IntStream.range(0, SIZE)
                .mapToObj(i -> boxes[i][col.value])
                .collect(Collectors.toList());
    }

    public List<Box> subgrid(Pos pos) {
        final var topLeft = Pos.of(
                (pos.row.value / SUBGRID_SIZE) * SUBGRID_SIZE,
                (pos.col.value / SUBGRID_SIZE) * SUBGRID_SIZE
        );
        var res = new ArrayList<Box>();
        for (int i = 0; i < SUBGRID_SIZE; ++i) {
            for (int j = 0; j < SUBGRID_SIZE; ++j) {
                res.add(box(topLeft.offset(i, j)));
            }
        }
        return res;
    }
}
