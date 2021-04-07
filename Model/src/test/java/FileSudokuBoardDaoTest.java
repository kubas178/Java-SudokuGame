import model.*;
import model.exceptions.FileException;
import model.exceptions.RowColumnBoxSizeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class FileSudokuBoardDaoTest {

    @Test
    public void getNameTest(){
        FileSudokuBoardDao d = new FileSudokuBoardDao("test");
        assertEquals("test.txt",d.getName());
    }

    @Test
    public void FileReadAndWriteTest() throws FileException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        board.set(5,5,5);

        FileSudokuBoardDao d = new FileSudokuBoardDao("testing");
        d.write(board);

        SudokuBoard board2;
        board2 = d.read();

        assertEquals(board2.get(5,5), 5);
    }

    @Test
    public void readExceptionTest() throws FileException {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> fileSudokuBoardDao = factory.getFileDao("yyy.txt");

        Assertions.assertThrows(FileException.class, () -> {
            fileSudokuBoardDao.read();
        });
    }

    @Test
    public void writeExceptionTest() throws FileException {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        Dao<SudokuBoard> fileSudokuBoardDao = factory.getFileDao("?");

        Assertions.assertThrows(FileException.class, () -> {
            fileSudokuBoardDao.write(sudokuBoard);
        });
    }

//    @Test(expected = FileOperationException.class)
//    public void writeIOExceptionTest() throws DaoException {
//        if (SystemUtils.IS_OS_WINDOWS) {
//            fileSudokuBoardDao = factory.getFileDao("?");
//        } else if (SystemUtils.IS_OS_LINUX) {
//            fileSudokuBoardDao = factory.getFileDao("/");
//        } else {
//            fileSudokuBoardDao = factory.getFileDao("?");
//        }
//        fileSudokuBoardDao.write(sudokuBoard);
//    }

    /*
    @Test
    public void readExceptionTest() {
        model.FileSudokuBoardDao d = new model.FileSudokuBoardDao("testing");
       Exception exception = assertThrows(RuntimeException.class, () -> d.read());

       assertEquals("Blad przy odczycie", exception.getMessage());

    }

    @Test
    public void writeExceptionTest() {
        model.SudokuBoard board = new model.SudokuBoard(new model.BacktrackingSudokuSolver());
        model.FileSudokuBoardDao d = new model.FileSudokuBoardDao("testing");

        d.write(board);
        try {
            d.write(board);
            fail("Expected an IndexOutOfBoundsException to be thrown");
        } catch (RuntimeException e) {
            assertEquals("blad", e.getMessage());
        }
    }

     */



}
