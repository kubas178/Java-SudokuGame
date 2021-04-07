package model;

import java.util.List;

public class SudokuRow extends SudokuRowColumBox {

    public static final int SIZE = 9;


    public SudokuRow(final List<SudokuField> fields) {
        super(fields);
    }


}
