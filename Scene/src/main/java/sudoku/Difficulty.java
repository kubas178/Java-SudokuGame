package sudoku;



public enum Difficulty {
    EASY(10,"_lvl1"),
    MEDIUM(15,"_lvl2"),
    HARD(30,"_lvl3");

    private int gaps;
    private String key;

    Difficulty(int gaps, String key) {
        this.gaps = gaps;
        this.key = key;
    }

    public int getGaps() {
        return gaps;
    }

    public String getKey() {
        return key;
    }
}
