package sudoku;

import java.io.File;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import model.*;
import model.exceptions.FileException;


public class BoardControllers {

    private StartGameControllers cont = new StartGameControllers();
    FileSudokuBoardDao daoClone = new FileSudokuBoardDao("clone");
    FileSudokuBoardDao daoOriginal = new FileSudokuBoardDao("original");
    private BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
    private ResourceBundle bundle = ChoosingLanguageControllers.getBundle();
    private SudokuBoard board = new SudokuBoard(solver);
    private SudokuBoard board2;
    TextField[][] textFields = new TextField[9][9];
    private SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    JdbcSudokuBoardDao dbDaoClone= new JdbcSudokuBoardDao("clone");
    JdbcSudokuBoardDao dbDaoOriginal= new JdbcSudokuBoardDao("original");
    private FileChooser fileChooser;
    private File file;



    @FXML
    private GridPane boardGrid;

    @FXML
    private Button giveUpButton;

    @FXML
    private void initialize() throws CloneNotSupportedException {
        board2 = BoardHandler.getClonedBoard();
        board2.setBoardName("clone");
        board = BoardHandler.getOriginalBoard();
        board.setBoardName("original");
        fill();
    }

    public void fill() {
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            for (int j = 0; j < SudokuBoard.SIZE; j++) {
                TextField textField = new TextField();
                restricText(textField);
                if (!board.isModificable(i, j)) {
                    textField.setDisable(true);
                }
                textField.setText(String.valueOf(board.get(i, j)));
                boardGrid.add(textField, i, j);
                textFields[i][j] = textField;
            }
        }
        if (!BoardHandler.isIsLoadedFromFile()) {
            Difficulty level = cont.getLevel();
            int gaps = level.getGaps();
            if (level == Difficulty.EASY) {
                while (gaps >= 0) {
                    generateGap();
                    gaps--;
                }
            } else if (level == Difficulty.MEDIUM) {
                while (gaps >= 0) {
                    generateGap();
                    gaps--;
                }
            } else if (level == Difficulty.HARD) {
                while (gaps >= 0) {
                    generateGap();
                    gaps--;
                }
            }
        }
    }

    public void generateGap() {
        Random random = new Random();
        int i = random.nextInt(9);
        int j = random.nextInt(9);
        board.setGap(i, j, 0);
        TextField textField = new TextField();
        restricText(textField);
        textField.setText(String.valueOf(board.get(i, j)));
        boardGrid.add(textField, i, j);
        textFields[i][j] = textField;
        //System.out.println(textField);
        //int number = Integer.parseInt(textField.getText());
        //System.out.println(textField.getText());
        //
        //System.out.println(number);
        //board.set(i, j, number);
    }

    public void actionGiveUp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("_tit1"));
        alert.setHeaderText(null);
        alert.setContentText(bundle.getString("_alert1"));
        alert.showAndWait();
        Platform.exit();
    }


    public void actionCheck() {
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            for (int j = 0; j < SudokuBoard.SIZE; j++) {
                //System.out.println(textFields[i][j].getText());
                board.set(i,j,Integer.parseInt(textFields[i][j].getText()));
            }
        }
        if (board.equals(board2)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("_tit3"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("_alert3"));
            alert.showAndWait();
            Platform.exit();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("_tit4"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("_alert4"));
            alert.showAndWait();
            }
        //System.out.println(board.toString());
        //System.out.println("______________________________");
        }

    @FXML
    public void actionSave() throws FileException {
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            for (int j = 0; j < SudokuBoard.SIZE; j++) {
                System.out.println(textFields[i][j].getText());
                board.set(i,j,Integer.parseInt(textFields[i][j].getText()));
            }
        }
        daoClone.write(board2);
        daoOriginal.write(board);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("_tit2"));
        alert.setHeaderText(null);
        alert.setContentText(bundle.getString("_alert2"));
        alert.showAndWait();
    }

    @FXML
    private void onActionButtonDatabase() throws FileException, SQLException {
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            for (int j = 0; j < SudokuBoard.SIZE; j++) {
                System.out.println(textFields[i][j].getText());
                board.set(i,j,Integer.parseInt(textFields[i][j].getText()));
            }
        }
        dbDaoClone.write(board2);
        dbDaoOriginal.write(board);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("_tit2"));
        alert.setHeaderText(null);
        alert.setContentText(bundle.getString("_alert2"));
        alert.showAndWait();
    }


    ////OGRANICZENIA NA WPISYWANY TEKST////

    public void restricText(TextField textField) {
        //tylko liczby
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        //Tylko jedna liczba
        textField.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 1) {
                return null;
            } else {
                return change;
            }
        }));
        textField.setAlignment(Pos.CENTER);
        textField.setMinSize(35, 35);
        textField.setFont(Font.font(22));
    }
}

