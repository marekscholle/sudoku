package com.marekscholle.sudoku;

public class App {
    public static void main(String[] args) {
        final var board = new Board();
        Rules.valueOnce(board);
    }
}
