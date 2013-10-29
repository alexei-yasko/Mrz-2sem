package yaskoam.mrz2.lab2.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.fxml.FXML;
import yaskoam.mrz2.lab2.ui.panels.ErrorChartPanel;
import yaskoam.mrz2.lab2.ui.panels.SequencePanel;
import yaskoam.mrz2.lab2.ui.panels.SettingsAndResultsPanel;
import yaskoam.mrz2.lab2.ui.panels.WeightMatrixPanel;
import yaskoam.mrz2.lab2.ui.toolbar.ToolBarPanel;

/**
 * @author Q-YAA
 */
public class MainPanel extends BaseComponent {

    @FXML
    private SequencePanel sequencePanel;

    @FXML
    private ToolBarPanel toolBarPanel;

    @FXML
    private SettingsAndResultsPanel settingsAndResultsPanel;

    @FXML
    private ErrorChartPanel errorChartPanel;

    @FXML
    private WeightMatrixPanel weightMatrixPanel;

    private UiLogger uiLogger;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        uiLogger = createUiLogger();
        toolBarPanel.setMainPanel(this);
        uiLogger.setChartPoints(errorChartPanel.getChartPoints());
    }

    public SequencePanel getSequencePanel() {
        return sequencePanel;
    }

    public WeightMatrixPanel getWeightMatrixPanel() {
        return weightMatrixPanel;
    }

    public SettingsAndResultsPanel getSettingsAndResultsPanel() {
        return settingsAndResultsPanel;
    }

    public ErrorChartPanel getErrorChartPanel() {
        return errorChartPanel;
    }

    public UiLogger getUiLogger() {
        return uiLogger;
    }

    private UiLogger createUiLogger() {
        StringProperty totalErrorProperty = new SimpleStringProperty();
        StringProperty meanErrorProperty = new SimpleStringProperty();
        StringProperty numberOfIterationsProperty = new SimpleStringProperty();

        settingsAndResultsPanel.getTotalErrorTextField().textProperty().bindBidirectional(totalErrorProperty);
        settingsAndResultsPanel.getMeanErrorTextField().textProperty().bindBidirectional(meanErrorProperty);
        settingsAndResultsPanel.getNumberOfIterationsTextField().textProperty().bindBidirectional(numberOfIterationsProperty);

        return new UiLogger(totalErrorProperty, meanErrorProperty, numberOfIterationsProperty);
    }
}
