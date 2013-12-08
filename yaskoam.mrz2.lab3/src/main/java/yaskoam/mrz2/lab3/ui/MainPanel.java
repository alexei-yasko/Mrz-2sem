package yaskoam.mrz2.lab3.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.fxml.FXML;
import yaskoam.mrz2.lab3.ui.panels.ImagePanel;
import yaskoam.mrz2.lab3.ui.toolbar.ToolBarPanel;

/**
 * @author Q-YAA
 */
public class MainPanel extends BaseComponent {

    @FXML
    private ImagePanel sourceImagePanel;
    @FXML
    private ToolBarPanel toolBarPanel;

    private UiLogger uiLogger;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        uiLogger = createUiLogger();
        toolBarPanel.setMainPanel(this);
    }

    public UiLogger getUiLogger() {
        return uiLogger;
    }

    private UiLogger createUiLogger() {
        StringProperty totalErrorProperty = new SimpleStringProperty();
        StringProperty meanErrorProperty = new SimpleStringProperty();
        StringProperty numberOfIterationsProperty = new SimpleStringProperty();

        return new UiLogger(totalErrorProperty, meanErrorProperty, numberOfIterationsProperty);
    }

    public ImagePanel getSourceImagePanel() {
        return sourceImagePanel;
    }
}
