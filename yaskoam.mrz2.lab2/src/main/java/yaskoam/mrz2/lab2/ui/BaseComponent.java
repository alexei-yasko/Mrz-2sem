package yaskoam.mrz2.lab2.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

/**
 * @author Q-YAA
 */
public class BaseComponent extends VBox implements Initializable {

    protected FXMLLoader fxmlLoader;

    public BaseComponent() {
        initComponent(getClass().getSimpleName() + ".fxml");
    }

    public BaseComponent(String fxmlFileName) {
        initComponent(fxmlFileName);
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
    }

    protected void initComponent(String fxmlFileName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.fxmlLoader = fxmlLoader;

        try {
            fxmlLoader.load();
        }
        catch (IOException e) {
            throw new IllegalStateException("Can't initialize component.", e);
        }
    }
}
