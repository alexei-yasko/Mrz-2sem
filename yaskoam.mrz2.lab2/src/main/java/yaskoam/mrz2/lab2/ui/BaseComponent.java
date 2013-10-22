package yaskoam.mrz2.lab2.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

/**
 * @author Q-YAA
 */
public class BaseComponent extends VBox {

    protected FXMLLoader fxmlLoader;

    public BaseComponent() {
        initComponent(getClass().getSimpleName() + ".fxml");
    }

    public BaseComponent(String fxmlFileName) {
        initComponent(fxmlFileName);
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
