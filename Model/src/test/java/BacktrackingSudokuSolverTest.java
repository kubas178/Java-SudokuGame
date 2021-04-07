import model.BacktrackingSudokuSolver;
import model.SudokuBoard;
import model.SudokuSolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {
    private SudokuBoard sudokuBoard;
    private SudokuSolver sudokuSolver;

    @BeforeEach
    public void setUp() {
        sudokuSolver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(sudokuSolver);
    }

    @Test

    public void IsBoardCorrect() {
        boolean correctBoard = true;

        sudokuSolver.solve(sudokuBoard);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                for (int i = col + 1; i < 9; i++) {
                    if (sudokuBoard.get(row, col) == sudokuBoard.get(row, i)) {
                        correctBoard = false;
                    }
                }
            }
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                for (int i = col + 1; i < 9; i++) {
                    if (sudokuBoard.get(col, row) == sudokuBoard.get(i, row)) {
                        correctBoard = false;
                    }
                }
            }
        }


        for (int boxInRow = 0; boxInRow < 3; boxInRow++) {
            for (int boxInCol = 0; boxInCol < 3; boxInCol++) {
                for (int cellInBox = 0; cellInBox < 9; cellInBox++) {
                    for (int nextCell = cellInBox + 1; nextCell < 9; nextCell++) {
                        if (sudokuBoard.get(boxInRow * 3 + (cellInBox / 3), boxInCol * 3 + (cellInBox % 3)) ==
                                sudokuBoard.get(boxInRow * 3 + (nextCell / 3), boxInCol * 3 + (nextCell % 3))) {
                            correctBoard = false;
                        }
                    }
                }
            }
        }
        assertTrue(correctBoard);
    }


}