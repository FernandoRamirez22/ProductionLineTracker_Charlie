package notSample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ProductionWithTabs.fxml"));
        primaryStage.setTitle("Production Line Tracker");

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);

        scene.getStylesheets().add(getClass().getResource("cobra.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
