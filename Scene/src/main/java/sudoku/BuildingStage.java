package sudoku;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sudoku.exceptions.BuildingStageException;


public class BuildingStage {

    private static Stage stage;
    private ResourceBundle bundle = ResourceBundle.getBundle("Language");

    private static void setStage(Stage stage) {
        BuildingStage.stage = stage;
    }

    private static Parent fxml(String path, ResourceBundle bundle) throws IOException {
       return new FXMLLoader(BuildingStage.class.getResource(path),bundle).load();
   }

   private static Parent fxml(String path) throws IOException {
        return new FXMLLoader(BuildingStage.class.getResource(path)).load();
    }

    public static Stage getStage() {
        return stage;
    }


    //Building stage without a title

    public static void buildStage(String path, ResourceBundle bundle) {
        setStage(stage);
        Scene scene = null;
        try {
            scene = new Scene(fxml(path,bundle));
        } catch (BuildingStageException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    //Building first stage with a title

    public static void buildStage(Stage stage, String path) {
        setStage(stage);
        Scene scene = null;
        try {
            scene = new Scene(fxml(path));
        } catch (BuildingStageException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("SUDOKU - PROKOM");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }


}
