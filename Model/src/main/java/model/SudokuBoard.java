package model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SudokuBoard implements Serializable,Cloneable {

    private String boardName;
    final Logger logger = LoggerFactory.getLogger(SudokuBoard.class);
    public static final int SIZE = 9;
    public static final int NUMBER_OF_CELLS = 81;
    private List<List<SudokuField>> board;
    private SudokuSolver sudokuSolver;

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public SudokuBoard(SudokuSolver sudokuSolver) {

        board = Arrays.asList(new List[SIZE]);

        for (int i = 0; i < SIZE; i++) {
            board.set(i, Arrays.asList(new SudokuField[SIZE]));
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board.get(i).set(j, new SudokuField());
            }
        }

        this.sudokuSolver = sudokuSolver;
    }

    public int getSize() {
        return SIZE;
    }

    public boolean isModificable(int row, int col) {
        return board.get(row).get(col).isModified();
    }

    public int get(int row, int col) {
        return board.get(row).get(col).getFieldValue();
    }

    public void set(int x, int y, int value) {
        this.board.get(x).get(y).setFieldValue(value);
    }

    public void setGap(int x, int y, int value) {
        this.board.get(x).get(y).setFieldValue(value);
        this.board.get(x).get(y).setModified(true);
    }

    public void solveGame() {
        logger.info("Board created");
        sudokuSolver.solve(this);
        checkBoard();
    }

    public boolean isFieldClear(int row, int column, int number) {

        for (int i = 0; i < getSize(); i++) {
            if (number == get(row,i)) {
                return false;
            }
        }

        for (int i = 0; i < getSize(); i++) {
            if (number == get(i,column)) {
                return false;
            }
        }

        int smallRow = (row / 3) * 3;
        int smallColumn = (column / 3) * 3;
        for (int i = smallRow; i < smallRow + 3; i++) {
            for (int j = smallColumn; j < smallColumn + 3; j++) {
                if (get(i,j) == number) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkBoard() {

        for (int i = 0; i < (SIZE / 3); i++) {
            for (int j = 0; j < (SIZE / 3); j++) {
                if (getBox(i,j).verify() == false) {
                    return false;
                }
            }
        }

        for (int i = 0; i < SIZE; i++) {
            if (getColumn(i).verify() == false) {
                return false;
            }
        }
        for (int i = 0; i < SIZE; i++) {
            if (getRow(i).verify() == false) {
                return false;
            }
        }
        return true;
    }

    public SudokuRow getRow(int y) {
        List<SudokuField> fields = Arrays.asList(new SudokuField[SIZE]);
        for (int i = 0; i < SIZE; i++) {
            fields.set(i, board.get(y).get(i));
        }
        return new SudokuRow(fields);
    }

    public SudokuColumn getColumn(int x) {

        List<SudokuField> fields = Arrays.asList(new SudokuField[SIZE]);
        for (int i = 0; i < SIZE; i++) {
            fields.set(i, board.get(i).get(x));
        }

        return new SudokuColumn(fields);
    }

    public SudokuBox getBox(int x, int y) {
        List<SudokuField> fields = Arrays.asList(new SudokuField[SIZE]);
        int z = 0;
        for (int i = 0; i < (SIZE / 3); i++) {
            for (int j = 0; j < (SIZE / 3); j++, z++) {
                fields.set(z, board.get(y * 3 + i).get(x * 3 + j));
            }
        }
        return new SudokuBox(fields);
    }

    @Override
    public  String toString() {
        return MoreObjects.toStringHelper(this).add("board", board).toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final SudokuBoard other  = (SudokuBoard) o;
        return Objects.equal(this.board, other.board);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(board);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sudokuBoard.set(i, j, get(i, j));
            }
        }
        return sudokuBoard;
    }

}













