package yaskoam.mrz2.lab2.ui.panels;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import yaskoam.mrz2.lab2.Settings;
import yaskoam.mrz2.lab2.ui.BaseComponent;
import yaskoam.mrz2.lab2.ui.support.DoubleTextFieldConstraint;
import yaskoam.mrz2.lab2.ui.support.IntTextFieldConstraint;

/**
 * @author Q-YAA
 */
public class SettingsAndResultsPanel extends BaseComponent {

    @FXML
    private TextField segmentHeightTextField;

    @FXML
    private TextField segmentWidthTextField;

    @FXML
    private TextField secondLayerNeuronsTextField;

    @FXML
    private TextField learningCoefficientTextField;

    @FXML
    private TextField maxErrorTextField;

    @FXML
    private TextField maxIterTextField;

    @FXML
    private TextField totalErrorTextField;

    @FXML
    private TextField meanErrorTextField;

    @FXML
    private TextField numberOfIterationsTextField;

    public SettingsAndResultsPanel() {
    }

    public void updateSettings() {
        Settings.get().setSegmentHeight(Integer.parseInt(segmentHeightTextField.getText()));
        Settings.get().setSegmentWidth(Integer.parseInt(segmentWidthTextField.getText()));
        Settings.get().setSecondLayerNeurons(Integer.parseInt(secondLayerNeuronsTextField.getText()));
        Settings.get().setLearningCoefficient(Double.parseDouble(learningCoefficientTextField.getText()));
        Settings.get().setMaxError(Double.parseDouble(maxErrorTextField.getText()));
        Settings.get().setMaxIterations(Integer.parseInt(maxIterTextField.getText()));
    }

    public void disableResultTextFields() {
        totalErrorTextField.setDisable(true);
        meanErrorTextField.setDisable(true);
        numberOfIterationsTextField.setDisable(true);
    }

    public void enableResultTextFields() {
        totalErrorTextField.setDisable(false);
        meanErrorTextField.setDisable(false);
        numberOfIterationsTextField.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        setTextFieldsEventHandlers();
    }

    public TextField getTotalErrorTextField() {
        return totalErrorTextField;
    }

    public TextField getNumberOfIterationsTextField() {
        return numberOfIterationsTextField;
    }

    public TextField getMeanErrorTextField() {
        return meanErrorTextField;
    }

    private void setTextFieldsEventHandlers() {
        segmentHeightTextField.textProperty().addListener(new IntTextFieldConstraint(segmentHeightTextField));
        segmentWidthTextField.textProperty().addListener(new IntTextFieldConstraint(segmentWidthTextField));
        secondLayerNeuronsTextField.textProperty().addListener(new IntTextFieldConstraint(secondLayerNeuronsTextField));
        maxIterTextField.textProperty().addListener(new IntTextFieldConstraint(maxIterTextField));

        learningCoefficientTextField.textProperty().addListener(new DoubleTextFieldConstraint(learningCoefficientTextField));
        maxErrorTextField.textProperty().addListener(new DoubleTextFieldConstraint(maxErrorTextField));
    }
}
