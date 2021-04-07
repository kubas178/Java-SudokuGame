package sudoku;

import model.SudokuBoard;

public class BoardHandler {
    private static SudokuBoard originalBoard;

    private static SudokuBoard clonedBoard;

    private static boolean isLoadedFromFile;

    public BoardHandler() {
    }

    public static SudokuBoard getOriginalBoard() {
        return originalBoard;
    }

    public static void setOriginalBoard(SudokuBoard originalBoard) {
        BoardHandler.originalBoard = originalBoard;
    }

    public static SudokuBoard getClonedBoard() {
        return clonedBoard;
    }

    public static void setClonedBoard(SudokuBoard clonedBoard) {
        BoardHandler.clonedBoard = clonedBoard;
    }

    public static boolean isIsLoadedFromFile() {
        return isLoadedFromFile;
    }

    public static void setIsLoadedFromFile(boolean isLoadedFromFile) {
        BoardHandler.isLoadedFromFile = isLoadedFromFile;
    }
}
