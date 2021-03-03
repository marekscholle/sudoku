# sudoku
Sudoku solver, a homework for some company hiring process.
Written in Java as requested.
Quite primitive solution that runs internal triggers on the stack, 
but the problem is small enough so that we don't get stack overflow.
Uses exceptions for internal error detection; 
but without language or library support we can't do much better
(in Scala, we would probably use `Either`). The primitive wrappers
(`Value`, `Row`, `Col`) would also be much simpler 
in some better JVM language.

## Setup
This is a gradle project, build with `./gradlew build` in root folder.
You can run with `./gradlew run` as:

```
./gradlew run --args='"  2    41|    82 7 |    4   9|2   793  | 1     8 |  681   4|1   9    | 6 43    |85    4  "'
```
At the end of application logs you will see the result:
```
[2021-03-03 15:02:48.916] [INFO] App - Result:
═══════════════════════════════════════════════════════════════════════════════════════════
║     6   |  3      | 2       ║        9|    5    |      7  ║       8 |   4     |1        ║
║   4     |        9|1        ║     6   |       8 | 2       ║    5    |      7  |  3      ║
║      7  |       8 |    5    ║  3      |   4     |1        ║ 2       |     6   |        9║
═══════════════════════════════════════════════════════════════════════════════════════════
║ 2       |   4     |       8 ║    5    |      7  |        9║  3      |1        |     6   ║
║  3      |1        |        9║ 2       |     6   |   4     ║      7  |       8 |    5    ║
║    5    |      7  |     6   ║       8 |1        |  3      ║        9| 2       |   4     ║
═══════════════════════════════════════════════════════════════════════════════════════════
║1        | 2       |   4     ║      7  |        9|    5    ║     6   |  3      |       8 ║
║        9|     6   |      7  ║   4     |  3      |       8 ║1        |    5    | 2       ║
║       8 |    5    |  3      ║1        | 2       |     6   ║   4     |        9|      7  ║
═══════════════════════════════════════════════════════════════════════════════════════════
```
The only argument is the input state of board, rows separated by `|`, 
with spaces for numbers to be filled in.

The application provides a hint if the input is invalid:
```
./gradlew run --args='"11       |         |         |         |         |         |         |         |         "'
```
yields
```
[2021-03-03 15:05:50.843] [INFO] App - set Pos(0,1) to Value(0)
Error while solving the puzzle, perhaps there is no solution for it.
java.lang.IllegalStateException: set different value
        at com.marekscholle.sudoku.Box.setValue(Box.java:78)
        ...
```
We can't reliably recognize a programming bug from invalid input.

## Implementation

The idea is simple, for every field in the 9x9 board (grid) we store which 
numbers are possible/impossible, and on any progress we make we run trigger
rule checkers that may do some more progress. E.g. if we set a number as 
impossible for a field and we detect there remains only one possible value
for the field, we set it ... and trigger another cascade of such checks.

If our primitive checkers can't make any progress, we take an appropriate
field and try to set possible values for it and solve the problem
as it were part of input. This of course has a recursive nature. If we run
into a problem (we tried a bad number), we restore the state from backup
and try next possible value. The solver is very quick for inputs I tried;
so this inherently single-threaded mutable solution looks good enough.

The application starts with an empty board where we set the values from input 
one by one. If the puzzle has no solution, we will detect a problem 
somewhere in the process, throw an exception and report the fail to user.
It may happen there are multiple solutions; if so, we output some of them
(which one depends on selection of the field we try to guess and order
of value we try for it).

This may be seen if we run the application with empty board (a useful, extreme case):

```
./gradlew run --args='"         |         |         |         |         |         |         |         |         "'
```

```
[2021-03-03 14:58:47.375] [INFO] App - Result:
═══════════════════════════════════════════════════════════════════════════════════════════
║1        | 2       |  3      ║   4     |    5    |     6   ║      7  |       8 |        9║
║   4     |    5    |     6   ║      7  |       8 |        9║1        | 2       |  3      ║
║      7  |       8 |        9║1        | 2       |  3      ║   4     |    5    |     6   ║
═══════════════════════════════════════════════════════════════════════════════════════════
║ 2       |  3      |1        ║     6   |      7  |   4     ║       8 |        9|    5    ║
║       8 |      7  |    5    ║        9|1        | 2       ║  3      |     6   |   4     ║
║     6   |        9|   4     ║    5    |  3      |       8 ║ 2       |1        |      7  ║
═══════════════════════════════════════════════════════════════════════════════════════════
║  3      |1        |      7  ║ 2       |     6   |    5    ║        9|   4     |       8 ║
║    5    |   4     | 2       ║       8 |        9|      7  ║     6   |  3      |1        ║
║        9|     6   |       8 ║  3      |   4     |1        ║    5    |      7  | 2       ║
═══════════════════════════════════════════════════════════════════════════════════════════
```

## TODO
- Tests for rules.
- Tests for input reader and vizualizer.
- Some tests where time is spent, maybe optimization 
  of some rule implementations.
- Better communication of errors, but not worth for small application and Java. 