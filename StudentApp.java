package sample;

/**
 * @author      Edson Zhu <efkzhu @ myseneca.ca>
 * @version     1.0
 * @since       1.0
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * StudentApp class which it render the window screen. And is all the main actions of the app
 */
public class StudentApp extends Application {

    /**
     * Function which deals with the FXML file
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(getClass().getResource("studentAppDisplay.fxml"));
        primaryStage.setTitle("Student Application");
        primaryStage.setScene(new Scene(root, 690, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
