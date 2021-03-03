package com.marekscholle.sudoku;

interface Rule {
    /**
     * Callback triggered when {@link Box} value is set.
     */
    void onSetValue(Pos pos, Value value);

    /**
     * Callback triggered when a value is set as impossible for {@link Box}.
     */
    void onSetImpossible(Pos pos, Value value);
}
