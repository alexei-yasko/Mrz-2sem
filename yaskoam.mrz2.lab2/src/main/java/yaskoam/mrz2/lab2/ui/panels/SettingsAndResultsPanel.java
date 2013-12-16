package yaskoam.mrz2.lab2.ui.panels;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import yaskoam.mrz2.lab2.ui.BaseComponent;
import yaskoam.mrz2.lab2.ui.UiUtils;
import yaskoam.mrz2.lab2.ui.support.DoubleTextFieldConstraint;
import yaskoam.mrz2.lab2.ui.support.IntTextFieldConstraint;

/**
 * @author Q-YAA
 */
public class SettingsAndResultsPanel extends BaseComponent {

    @FXML
    private TextField logStepTextField;

    @FXML
    private TextField delayTextField;

    @FXML
    private TextField imagesNumberTextField;

    @FXML
    private TextField windowSizeTextField;

    @FXML
    private TextField learningCoefficientTextField;

    @FXML
    private TextField maxErrorTextField;

    @FXML
    private TextField maxIterTextField;

    @FXML
    private TextField totalErrorTextField;

    @FXML
    private TextField numberOfIterationsTextField;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        setTextFieldsEventHandlers();
    }
    
    public int getImagesNumber() {
        return UiUtils.getIntValue(imagesNumberTextField);
    }

    public int getWindowSize() {
        return UiUtils.getIntValue(windowSizeTextField);
    }

    public double getLearningCoefficient() {
        return UiUtils.getDoubleValue(learningCoefficientTextField);
    }

    public double getMaxError() {
        return UiUtils.getDoubleValue(maxErrorTextField);
    }

    public int getMaxIter() {
        return UiUtils.getIntValue(maxIterTextField);
    }

    public int getLogStep() {
        return UiUtils.getIntValue(logStepTextField);
    }

    public int getDelay() {
        return UiUtils.getIntValue(delayTextField);
    }

    public void disableResultTextFields() {
        totalErrorTextField.setDisable(true);
        numberOfIterationsTextField.setDisable(true);
    }

    public void enableResultTextFields() {
        totalErrorTextField.setDisable(false);
        numberOfIterationsTextField.setDisable(false);
    }

    public TextField getTotalErrorTextField() {
        return totalErrorTextField;
    }

    public TextField getNumberOfIterationsTextField() {
        return numberOfIterationsTextField;
    }

    private void setTextFieldsEventHandlers() {
        windowSizeTextField.textProperty().addListener(new IntTextFieldConstraint(windowSizeTextField));
        imagesNumberTextField.textProperty().addListener(new IntTextFieldConstraint(imagesNumberTextField));
        maxIterTextField.textProperty().addListener(new IntTextFieldConstraint(maxIterTextField));
        delayTextField.textProperty().addListener(new IntTextFieldConstraint(delayTextField));
        logStepTextField.textProperty().addListener(new IntTextFieldConstraint(logStepTextField));

        learningCoefficientTextField.textProperty().addListener(new DoubleTextFieldConstraint(learningCoefficientTextField));
        maxErrorTextField.textProperty().addListener(new DoubleTextFieldConstraint(maxErrorTextField));
    }
}
