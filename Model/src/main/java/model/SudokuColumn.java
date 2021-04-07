package model;

import java.util.List;

public class SudokuColumn extends SudokuRowColumBox {

    public static final int SIZE = 9;


    public SudokuColumn(final List<SudokuField> fields) {
        super(fields);
    }



}
