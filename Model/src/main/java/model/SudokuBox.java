package model;

import java.util.List;

public class SudokuBox extends SudokuRowColumBox {

    public static final int SIZE = 9;

    public SudokuBox(final List<SudokuField> fields) {
        super(fields);
    }



}
