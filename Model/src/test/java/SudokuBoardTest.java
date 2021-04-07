import model.BacktrackingSudokuSolver;
import model.SudokuBoard;
import model.SudokuField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    private SudokuBoard sudokuBoard;

    @BeforeEach
    public void setUp() {
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    }

    @Test
    public void fillBoardIsRowCorrect() {
        boolean correctBoard = true;
        sudokuBoard.solveGame();

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

    @Test
    public void isFieldClearShouldReturnFalseWhenNumberExistsInRow() {
        sudokuBoard.set(0,0,5);
        assertFalse(sudokuBoard.isFieldClear(0,1,5));
    }

    @Test
    public void isFieldClearShouldReturnFalseWhenNumberExistsInColumn() {
        sudokuBoard.set(0,0,5);
        assertFalse(sudokuBoard.isFieldClear(1,0,5));
    }
    @Test
    public void isFieldClearShouldReturnFalseWhenNumberExistsInSmallBox() {
        sudokuBoard.set(0,0,5);
        assertFalse(sudokuBoard.isFieldClear(1,1,5));
    }

    @Test
    public void isFieldClearShouldReturnTrueWhenNumberDoesntExistsInRowColumnSmallBox() {
        sudokuBoard.set(0,0,5);
        sudokuBoard.set(3,1,5);
        assertTrue(sudokuBoard.isFieldClear(6,2,5));
    }


    @Test
    public void fillBoardShouldGenerateDifferentBoards() {
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());

        sudokuBoard.solveGame();
        sudokuBoard2.solveGame();
        boolean boardsEqual = true;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudokuBoard.get(row, col) != sudokuBoard2.get(row,col)) {
                    boardsEqual = false;
                }
            }
        }
        assertFalse(boardsEqual);
    }

    @Test
    public void getRowTest() {
        assertNotNull(sudokuBoard.getRow(1));
    }

    @Test
    public void getColumnTest() {
        assertNotNull(sudokuBoard.getColumn(1));
    }

    @Test
    public void getBoxTest() {
        assertNotNull(sudokuBoard.getBox(1, 1));
    }


    @Test
    public void DoesCheckBoardReturnFalseWhenRowIsIncorrect() {
        sudokuBoard.solveGame();
        int x = sudokuBoard.get(0,0);
        int y = sudokuBoard.get(1,0);
        sudokuBoard.set(0,0,y);
        sudokuBoard.set(1,0,x);
        assertFalse(sudokuBoard.checkBoard());
    }

    @Test
    public void DoesCheckBoardReturnFalseWhenColumnIsIncorrect() {
        sudokuBoard.solveGame();
        int x = sudokuBoard.get(3,0);
        int y = sudokuBoard.get(3,1);
        sudokuBoard.set(3,0,y);
        sudokuBoard.set(3,1,x);
        assertFalse(sudokuBoard.checkBoard());
    }

    @Test
    public void DoesCheckBoardReturnFalseWhenBoxIsIncorrect() {
        sudokuBoard.solveGame();
        int x = sudokuBoard.get(2,2);
        int y = sudokuBoard.get(3,3);
        if(x != y) {
            sudokuBoard.set(2, 2, y);
            sudokuBoard.set(3, 3, x);
        }
        else {
             x = sudokuBoard.get(2, 7);
             y = sudokuBoard.get(3, 6);
            sudokuBoard.set(2, 7, y);
            sudokuBoard.set(3, 6, x);
        }

        assertFalse(sudokuBoard.checkBoard());
    }

    @Test
    public void DoesEqualsReturnFalseWhenBoardsAreDifferent(){
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard2.solveGame();
        sudokuBoard.solveGame();
        assertFalse(sudokuBoard.equals(sudokuBoard2));
    }

    @Test
    public void DoesEqualsReturnFalseWhenBoardIsNull(){
        SudokuBoard sudokuBoard2 = null;
        sudokuBoard.solveGame();
        assertFalse(sudokuBoard.equals(sudokuBoard2));
    }

    @Test
    public void DoesEqualsReturnFalseWhenThereAreWrongClasses(){
        SudokuField field = new SudokuField();
        assertFalse(sudokuBoard.equals(field));
    }

    @Test
    public void DoesEqualsReturnTrueWhenBoardsAreTheSame(){
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard2 = sudokuBoard;
        sudokuBoard.solveGame();
        assertTrue(sudokuBoard.equals(sudokuBoard2));
    }

    @Test
    public void hashCodeBoardTest() {
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard2 = sudokuBoard;
        assertEquals(sudokuBoard.hashCode(), sudokuBoard2.hashCode());
    }

    @Test
    public void toStringBoardTest(){
       // sudokuBoard.solveGame();
        String boardValues = "SudokuBoard{board=[[";
        for (int i = 0; i<9; i++) {
            for (int j = 0; j<9; j++) {
                boardValues = boardValues + "SudokuField{fieldValue=" + sudokuBoard.get(i,j) + "}";
                if(j == 8 && i < 8) {
                    boardValues = boardValues + "], ";
                }
                else if (j == 8 && i == 8) {
                    boardValues = boardValues + "";
                }
                else {
                    boardValues = boardValues + ", ";
                }
            }
            if (i == 8) {
                boardValues = boardValues + "]]}";
            }
            else {
                boardValues = boardValues + "[";
            }
        }
        assertEquals(sudokuBoard.toString(), boardValues);
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        SudokuBoard board2;
        board2 = (SudokuBoard) sudokuBoard.clone();
        assertTrue(sudokuBoard.equals(board2));
    }

    @Test
    public void settingGapAndIsModificableTest() {
        sudokuBoard.setGap(1,1,1);
        assertEquals(true, sudokuBoard.isModificable(1,1));

    }


}


