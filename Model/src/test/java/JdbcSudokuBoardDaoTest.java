
import model.*;
import model.exceptions.DataBaseException;
import model.exceptions.FileException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class JdbcSudokuBoardDaoTest {


    JdbcSudokuBoardDao dbDaoClone= new JdbcSudokuBoardDao("clone");
    JdbcSudokuBoardDao dbDaoOriginal= new JdbcSudokuBoardDao("original");

    @Test
    public void prepareConnectionTest() throws SQLException, FileException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        board.setGap(1,1,0);
        dbDaoClone.write(board);
        SudokuBoard board2 = dbDaoClone.read();
        assertEquals(board,board2);
    }

    @Test
    public void writeExceptionsTests() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        Dao<SudokuBoard> dao = factory.getDatabaseDao("'");

        Assertions.assertThrows(DataBaseException.class, () -> {
            dao.write(sudokuBoard);
        });
    }

    @Test
    public void readExceptionsTests() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        Dao<SudokuBoard> dao = factory.getDatabaseDao("aa");

        Assertions.assertThrows(DataBaseException.class, () -> {
            dao.read();
        });
    }

}
