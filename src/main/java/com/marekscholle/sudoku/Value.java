package com.marekscholle.sudoku;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static com.marekscholle.sudoku.Const.SIZE;

class Value {
    final int value;

    private static final Value[] VALUES =
            IntStream.range(0, SIZE).mapToObj(Value::new).toArray(Value[]::new);

    private Value(int value) {
        this.value = value;
    }

    static Value of(int value) {
        return VALUES[value];
    }

    static List<Value> values() {
        return Arrays.asList(VALUES);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Value value1 = (Value) o;
        return value == value1.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Value(" + (value + 1) + ')';
    }
}