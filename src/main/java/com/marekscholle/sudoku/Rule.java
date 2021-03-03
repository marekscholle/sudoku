package com.marekscholle.sudoku;

import com.marekscholle.sudoku.Const.Value;

interface Rule {
    void onSetValue(Pos pos, Value value);

    void onSetImpossible(Pos pos, Value value);
}
