package com.marekscholle.sudoku;

interface Rule {
    void onSetValue(Pos pos, Value value);

    void onSetImpossible(Pos pos, Value value);
}
