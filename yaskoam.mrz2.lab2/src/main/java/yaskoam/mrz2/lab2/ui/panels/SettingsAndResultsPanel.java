package yaskoam.mrz2.lab2.ui.panels;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        setTextFieldsEventHandlers();
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
