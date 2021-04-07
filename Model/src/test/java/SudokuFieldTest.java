import model.BacktrackingSudokuSolver;
import model.SudokuBoard;
import model.SudokuField;
import model.exceptions.FieldValueException;
import model.exceptions.FileException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    private SudokuField SudokuField;

    @BeforeEach
    public void setUp() {
        SudokuField = new SudokuField(5);
    }

    @Test
    public void doesSetFieldValueSetsCorrectValue(){
        SudokuField.setFieldValue(3);
        assertTrue(SudokuField.getFieldValue() == 3);
    }

    @Test
    public void doesGetFieldValueGetsCorrectValue(){
        assertTrue(SudokuField.getFieldValue() == 5);
    }

    @Test
    public void DoesEqualsReturnFalseWheFieldsAreDifferent() {
        SudokuField field = new SudokuField(5);
        SudokuField field2 = new SudokuField(7);
        assertFalse(field.equals(field2));
    }

    @Test
    public void DoesEqualsReturnTrueWhenFieldsAreTheSame() {
        model.SudokuField field = new SudokuField(5);
        SudokuField field2 = new SudokuField(5);
        assertTrue(field.equals(field2));
    }

    @Test
    public void DoesEqualsReturnFalseWhenFieldIsNull(){
        SudokuField field = new SudokuField(5);
        SudokuField field2 = null;
        assertFalse(field.equals(field2));
    }

    @Test
    public void DoesEqualsReturnFalseWhenThereAreWrongClasses(){
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuField field = new SudokuField();
        assertFalse(field.equals(board));
    }

    @Test
    public void hashCodeSudokuRowColumnBoxTest() {
        //model.SudokuBoard sudokuBoard2 = new model.SudokuBoard(new model.BacktrackingSudokuSolver());
        //sudokuBoard2 = sudokuBoard;
        SudokuField field = new SudokuField(5);
        SudokuField field2 = new SudokuField(5);
        assertEquals(field.hashCode(), field2.hashCode());
    }

    @Test
    public void toStringBoardTest() {
        // sudokuBoard.solveGame();
        SudokuField field = new SudokuField(5);
        String fieldValue = "SudokuField{fieldValue=" + field.getFieldValue() + "}" ;
        assertEquals(field.toString(), fieldValue);
    }

    @Test
    public void comparisionTestShouldReturn0() {
        SudokuField field2 = new SudokuField();

        SudokuField.setFieldValue(1);
        field2.setFieldValue(1);
        assertEquals(SudokuField.compareTo(field2), 0);
    }

    @Test
    public void comparisionTestShouldReturn1() {
        SudokuField field2 = new SudokuField();
        SudokuField.setFieldValue(1);
        field2.setFieldValue(2);
        assertEquals(SudokuField.compareTo(field2), 1);
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        SudokuField.setFieldValue(1);
        SudokuField field2 = (SudokuField) SudokuField.clone();
        assertTrue(SudokuField.equals(field2));
    }

    @Test
    public void creatingGapAndMakingFieldsModifable(){
        SudokuField.setValue(3);
        assertEquals(3,SudokuField.getValue());
        SudokuField.setModified(true);
        assertEquals(true, SudokuField.isModified());
    }

    @Test
    public void setTooHighFieldValueTest() {

        Assertions.assertThrows(FieldValueException.class, () -> {
            SudokuField.setValue(10);
        });

    }

    @Test
    public void setTooLowFieldValueTest() {
        Assertions.assertThrows(FieldValueException.class, () -> {
            SudokuField.setValue(-1);
        });
    }

//    @Test(expected = BadFieldValueException.class)
//    public void setBadFieldValueTestSecond() {
//        sudokuField.setFieldValue(10);
//    }



}