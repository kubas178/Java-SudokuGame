module Scene {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires Model;
    requires slf4j.api;
    requires java.sql;

    opens sudoku;
}
