package yaskoam.mrz2.lab2.ui.panels;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.common.base.Strings;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import yaskoam.mrz2.lab2.functions.FibonacciFunction;
import yaskoam.mrz2.lab2.functions.Function;
import yaskoam.mrz2.lab2.ui.BaseComponent;
import yaskoam.mrz2.lab2.ui.support.IntTextFieldConstraint;

/**
 * @author Q-YAA
 */
public class SequencePanel extends BaseComponent {

    private static final Map<String, Function<Integer>> FUNCTIONS_MAP = new HashMap<String, Function<Integer>>(){{
        put("Fibonacci", new FibonacciFunction());
    }};

    @FXML
    private TextField xParameterTextField;

    @FXML
    private TextField nParameterTextField;

    @FXML
    private RadioButton powerRadioButton;

    @FXML
    private RadioButton periodicRadioButton;

    @FXML
    private RadioButton factorialRadioButton;

    @FXML
    private RadioButton fibonacciRadioButton;

    @FXML
    private TextArea resultSequenceTextArea;

    private ToggleGroup functionsToggleGroup;

    public void generateSequence() {
        resultSequenceTextArea.clear();

        String xString = xParameterTextField.textProperty().getValue();
        String nString = nParameterTextField.textProperty().getValue();
        int x = Strings.isNullOrEmpty(xString) ? 0 : Integer.parseInt(xString);
        int n = Strings.isNullOrEmpty(nString) ? 0 : Integer.parseInt(nString);

        Function<Integer> function = FUNCTIONS_MAP.get(getSelectedFunction());

        if (function != null) {
            List<Integer> sequence = function.generateSequence(x, n);
            resultSequenceTextArea.textProperty().setValue(sequence.toString());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        initRadioButtons();
        initTextFields();
    }

    private void initRadioButtons() {
        functionsToggleGroup = new ToggleGroup();

        fibonacciRadioButton.textProperty().setValue("Fibonacci");
        fibonacciRadioButton.setToggleGroup(functionsToggleGroup);
        fibonacciRadioButton.setSelected(true);

        factorialRadioButton.textProperty().setValue("Factorial");
        factorialRadioButton.setToggleGroup(functionsToggleGroup);

        periodicRadioButton.textProperty().setValue("Periodic");
        periodicRadioButton.setToggleGroup(functionsToggleGroup);

        powerRadioButton.textProperty().setValue("Power");
        powerRadioButton.setToggleGroup(functionsToggleGroup);
    }

    private void initTextFields() {
        xParameterTextField.textProperty().addListener(new IntTextFieldConstraint(xParameterTextField));
        nParameterTextField.textProperty().addListener(new IntTextFieldConstraint(nParameterTextField));
    }

    private String getSelectedFunction() {
        return ((RadioButton) functionsToggleGroup.getSelectedToggle()).textProperty().getValue();
    }
}
