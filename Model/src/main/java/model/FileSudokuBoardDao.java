package model;

import model.exceptions.FileException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private String name;

    public FileSudokuBoardDao(String name) {
        this.name = name + ".txt";
    }

    public String getName() {
        return name;
    }

    @Override
    public SudokuBoard read() throws FileException {

        SudokuBoard board = null;
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(name))) {
            board = (SudokuBoard)  reader.readObject();
        //  reader.close();

        } catch (Exception e) {
            throw new FileException(e);
        }

        return board;
    }

    @Override
    public void write(SudokuBoard board) throws FileException {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(name))) {
            writer.writeObject(board);
        //    writer.close();
        } catch (Exception e) {
            throw new FileException(e);
        }
    }

    /*
 @Override
    public void finalize() {
        try {
            super.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

   */
}
