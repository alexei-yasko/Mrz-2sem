package yaskoam.mrz2.lab2;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yaskoam.mrz2.lab2.ui.MainPanel;

/**
 * @author Q-YAA
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new MainPanel();
        stage.setTitle("Lab 2");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
