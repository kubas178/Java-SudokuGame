package sudoku;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import model.*;
import model.exceptions.DataBaseException;
import model.exceptions.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.LoadingGameException;


public class StartGameControllers {

    private Authors authors = new Authors();
    FileSudokuBoardDao daoClone;// = new FileSudokuBoardDao("clone");
    FileSudokuBoardDao daoOriginal;// = new FileSudokuBoardDao("original");
    JdbcSudokuBoardDao dbDaoClone;
    JdbcSudokuBoardDao dbDaoOriginal;
    private static SudokuBoard clonedSudokuBoard;
    private static SudokuBoard originalSudokuBoard;
    private static Difficulty diff;
    private FileChooser fileChooser;
    final Logger logger = LoggerFactory.getLogger(StartGameControllers.class);
    private Scene sudokuScene;
    private ResourceBundle bundle = ChoosingLanguageControllers.getBundle();
    private Dao<SudokuBoard> jdbcSudokuBoardDao;
    private SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private static SudokuBoard sudokuBoardFromSource;
    private static String level;
    private String language;

    @FXML
    private ImageView imageSudoku;

    @FXML
    private Text authorsTextField;

    @FXML
    private Text authorsTextField1;

    @FXML
    private ComboBox<Difficulty> difficultyLevel;

    @FXML
    private Button startGameButton;

    @FXML
    private void initialize() {
        difficultyLevel.getItems().addAll(FXCollections.observableArrayList(
                Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD));
        difficultyLevel.setConverter(new StringConverter<Difficulty>() {
            @Override
            public String toString(Difficulty object) {
                return bundle.getString(object.getKey());
            }

            @Override
            public Difficulty fromString(String string) {
                return difficultyLevel.getItems().stream().filter(ap ->
                        ap.getKey().equals(string)).findFirst().orElse(null);
            }
        });
        difficultyLevel.setValue(Difficulty.EASY);
        Image image = new Image(getClass().getResource("/Images/Images.png").toExternalForm());
        imageSudoku.setImage(image);
        authorsTextField.setText((String) authors.getObject("1. "));
        difficultyLevel.setStyle(
                "-fx-background-color:  #3D4956; -fx-font: 13px \"Liberation Sans\";");
    }

    @FXML
    public void actionStartGame() throws IOException, CloneNotSupportedException {
        diff = difficultyLevel.getSelectionModel().getSelectedItem();
        if (diff == Difficulty.EASY || diff == Difficulty.MEDIUM || diff == Difficulty.HARD) {
            var board = new SudokuBoard(new BacktrackingSudokuSolver());
            logger.info("Starting Game");
            board.solveGame();
            BoardHandler.setOriginalBoard(board);
            BoardHandler.setClonedBoard((SudokuBoard) board.clone());
            BuildingStage.buildStage("/sudoku/GameScene.fxml", bundle);
        }
    }


    @FXML
    private void onActionButtonDatabase() {
        try {
            logger.info("Loading Game from database");
            dbDaoClone = new JdbcSudokuBoardDao("clone");
            dbDaoOriginal = new JdbcSudokuBoardDao("original");
            clonedSudokuBoard = dbDaoClone.read();
            originalSudokuBoard = dbDaoOriginal.read();
            BoardHandler.setClonedBoard(clonedSudokuBoard);
            BoardHandler.setOriginalBoard(originalSudokuBoard);
            BoardHandler.setIsLoadedFromFile(true);
            BuildingStage.buildStage("/sudoku/GameScene.fxml", bundle);
        } catch (DataBaseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("_tit6"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("_alert6"));
            alert.showAndWait();
            Platform.exit();
        }
    }



    @FXML
    public void actionLoadGame() {
        try {
            logger.info("Loading Game from file");
            daoClone = new FileSudokuBoardDao("clone");
            daoOriginal = new FileSudokuBoardDao("original");
            clonedSudokuBoard = daoClone.read();
            originalSudokuBoard = daoOriginal.read();
            difficultyLevel.setDisable(true);
            BoardHandler.setOriginalBoard(originalSudokuBoard);
            BoardHandler.setClonedBoard(clonedSudokuBoard);
            BoardHandler.setIsLoadedFromFile(true);
            BuildingStage.buildStage("/sudoku/GameScene.fxml", bundle);
        } catch (LoadingGameException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("_tit5"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("_alert5"));
            alert.showAndWait();
            Platform.exit();
        } catch (FileException e) {
            e.printStackTrace();
        }
    }

    public Difficulty getLevel() {
        logger.info("Level choosen: " + diff);
        return diff;
    }

}
