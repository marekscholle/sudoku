package com.marekscholle.sudoku;

import java.util.Objects;

public class Const {
    static final int SIZE = 9;
    static final int SUBGRID_SIZE = 3;

    static class Value {
        private final int value;

        protected Value(int value) {
            this.value = value;
        }

        static Value of(int value) {
            return new Value(value);
        }

        int intValue() {
            return value;
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
            return "Value(" + value + ')';
        }
    }
}
