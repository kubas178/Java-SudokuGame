package sudoku;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.BuildingStageException;


public class ChoosingLanguageControllers {

    final Logger logger = LoggerFactory.getLogger(ChoosingLanguageControllers.class);
    private static ResourceBundle bundle;

    @FXML
    private Button englishLanguage;

    @FXML
    private Button polishLanguage;

    @FXML
    private void initialize(){
    }

    @FXML
    public void openMainWindowInPolish() {
        Locale locale = new Locale("pl");
        logger.info("Language: Polish");
        BuildingStage.buildStage("/sudoku/mainScene.fxml", setBundle(locale));
    }

    @FXML
    public void openMainWindowInEnglish() {
        Locale locale = new Locale("eng");
        logger.info("Language: English");
        BuildingStage.buildStage("/sudoku/mainScene.fxml", setBundle(locale));
    }

    public ResourceBundle setBundle(Locale l) {
         bundle = ResourceBundle.getBundle("Language", l);
         return bundle;
    }

    public static ResourceBundle getBundle() {
        return bundle;
    }

}
