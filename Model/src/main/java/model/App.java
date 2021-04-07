package model;

import model.exceptions.FileException;

import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) throws FileException {
        List<SudokuField> fields;
        fields = Arrays.asList(
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9));
        SudokuRow row = new SudokuRow(fields);
        fields.get(0).setFieldValue(2);
        FieldsObserver observer = new FieldsObserver();
        System.out.println(fields.get(0).getFieldValue());
        row.attach(observer);
        row.verify();
        fields.get(0).setFieldValue(1);
        row.verify();
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();
        SudokuField field = new SudokuField(5);
        System.out.println(field.toString());
        FileSudokuBoardDao d = new FileSudokuBoardDao("testing");
        d.write(board);
        SudokuBoard board2 = d.read();
    }
}
