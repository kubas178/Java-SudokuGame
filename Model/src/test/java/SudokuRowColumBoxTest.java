import model.*;
import model.exceptions.FieldValueException;
import model.exceptions.RowColumnBoxSizeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowColumBoxTest {

    private List<SudokuField> fields;


    @BeforeEach
    public void setUp() {
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
    }

    // TESTING ROW

    @Test
    public void doesVerifyAllForRowReturnFalseWhenValuesAreIncorrect() {
        SudokuRow row = new SudokuRow(fields);
        fields.get(0).setFieldValue(5);
        assertFalse(row.verify());
    }

    @Test
    public void doesVerifyAllForRowReturnTrueWhenValuesAreCorrect() {
        SudokuRow row = new SudokuRow(fields);
        for (int i = 0; i < 9; i++) {
            fields.get(i).setFieldValue(i + 1);
        }
        assertTrue(row.verify());
    }

    @Test
    public void doesVerifyForRowReturnTrueWhenVerifyAllIsCorrect() {
        SudokuRow row = new SudokuRow(fields);
        assertTrue(row.verify());
    }

    @Test
    public void doesVerifyForRowReturnFalseWhenVerifyAllIsInCorrect() {
        SudokuRow row = new SudokuRow(fields);
        fields.get(0).setFieldValue(2);
        assertFalse(row.verify());
    }

    // TESTING COLUMN

    @Test
    public void doesVerifyAllForColumnReturnFalseWhenValuesAreIncorrect() {
        SudokuColumn column = new SudokuColumn(fields);
        fields.get(0).setFieldValue(5);
        assertFalse(column.verify());
    }

    @Test
    public void doesVerifyAllForColumnReturnTrueWhenValuesAreCorrect() {
        SudokuColumn column = new SudokuColumn(fields);
        for (int i = 0; i < 9; i++) {
            fields.get(i).setFieldValue(i + 1);
        }
        assertTrue(column.verify());
    }

    @Test
    public void doesVerifyForColumnReturnTrueWhenVerifyAllIsCorrect() {
        SudokuColumn column = new SudokuColumn(fields);
        assertTrue(column.verify());
    }

    @Test
    public void doesVerifyForColumnReturnFalseWhenVerifyAllIsInCorrect() {
        SudokuColumn column = new SudokuColumn(fields);
        fields.get(0).setFieldValue(2);
        assertFalse(column.verify());
    }


    //TESTING BOX
    @Test
    public void doesVerifyAllForBoxReturnFalseWhenValuesAreIncorrect() {
        SudokuColumn column = new SudokuColumn(fields);
        fields.get(0).setFieldValue(5);
        assertFalse(column.verify());
    }

    @Test
    public void doesVerifyAllForBoxReturnTrueWhenValuesAreCorrect() {
        SudokuBox box = new SudokuBox(fields);
        for (int i = 0; i < 9; i++) {
            fields.get(i).setFieldValue(i + 1);
        }
        assertTrue(box.verify());
    }

    @Test

    public void doesVerifyForBoxReturnTrueWhenVerifyAllIsCorrect() {
        SudokuBox box = new SudokuBox(fields);
        assertTrue(box.verify());
    }

    @Test
    public void doesVerifyForBoxReturnFalseWhenVerifyAllIsInCorrect() {
        SudokuBox box = new SudokuBox(fields);
        fields.get(0).setFieldValue(2);
        assertFalse(box.verify());
    }

    @Test
    public void DoesEqualsReturnFalseWheColumnsBoxesOrRowsAreDifferent() {
        SudokuColumn column = new SudokuColumn(fields);
        List<SudokuField> fields2 = Arrays.asList(
                new SudokuField(2),
                new SudokuField(1),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9));
        SudokuColumn column2 = new SudokuColumn(fields2);
        assertFalse(column.equals(column2));
    }

    @Test
    public void DoesEqualsReturnTrueWhenColumnsBoxesOrRowsAreTheSame() {
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuColumn column = new SudokuColumn(fields);
        SudokuColumn column2 = new SudokuColumn(fields);
        assertTrue(column.equals(column2));
    }

    @Test
    public void DoesEqualsReturnFalseWhenColumnIsNull(){
        SudokuColumn column = new SudokuColumn(fields);
        SudokuColumn column2 = null;
        assertFalse(column.equals(column2));
    }

    @Test
    public void DoesEqualsReturnFalseWhenThereAreWrongClasses(){
        SudokuColumn column = new SudokuColumn(fields);
        SudokuField field = new SudokuField();
        assertFalse(column.equals(field));
    }

    @Test
    public void hashCodeSudokuRowColumnBoxTest() {
        //model.SudokuBoard sudokuBoard2 = new model.SudokuBoard(new model.BacktrackingSudokuSolver());
        //sudokuBoard2 = sudokuBoard;
        SudokuColumn column = new SudokuColumn(fields);
        SudokuColumn column2 = new SudokuColumn(fields);
        assertEquals(column.hashCode(), column2.hashCode());
    }

    @Test
    public void toStringBoardTest() {
        // sudokuBoard.solveGame();
        SudokuColumn column = new SudokuColumn(fields);
        String columnValues = "SudokuColumn{fields=[";
        for (int i = 0; i < 9; i++) {
            if (i < 8) {
                columnValues = columnValues + fields.get(i) + ", ";
            } else {
                columnValues = columnValues + fields.get(i) + "]}";
            }
        }
        assertEquals(column.toString(), columnValues);
    }

    @Test
    public void CloneTest() throws CloneNotSupportedException {
        SudokuColumn column = new SudokuColumn(fields);
        SudokuColumn column2;
        column2 = (SudokuColumn) column.clone();

        assertTrue(column.equals(column2));
    }


    @Test
    public void setTooLowFieldValueTest() {
        List<SudokuField> fields2 = Arrays.asList(
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(9));
        // 8 fields in row/column.box
        Assertions.assertThrows(RowColumnBoxSizeException.class, () -> {
            new SudokuRow(fields2);
        });

    }

}
