package yaskoam.mrz2.lab2.ui.panels;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.ArrayUtils;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import yaskoam.mrz2.lab2.functions.FactorialSequence;
import yaskoam.mrz2.lab2.functions.FibonacciSequence;
import yaskoam.mrz2.lab2.functions.PeriodicSequence;
import yaskoam.mrz2.lab2.functions.PowerSequence;
import yaskoam.mrz2.lab2.functions.Sequence;
import yaskoam.mrz2.lab2.ui.BaseComponent;
import yaskoam.mrz2.lab2.ui.UiUtils;
import yaskoam.mrz2.lab2.ui.support.IntTextFieldConstraint;

/**
 * @author Q-YAA
 */
public class SequencePanel extends BaseComponent {

    private static final Map<String, Sequence<Long>> FUNCTIONS_MAP = new HashMap<String, Sequence<Long>>() {{
        put("Fibonacci", new FibonacciSequence());
        put("Factorial", new FactorialSequence());
        put("Periodic", new PeriodicSequence());
        put("Power", new PowerSequence(2));
    }};

    @FXML
    private TextField generateToTextField;

    @FXML
    private TextField generateFromTextField;

    @FXML
    private TextField predictedAmountTextField;

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

    @FXML
    public TextArea predictedSequenceTextArea;

    private ToggleGroup functionsToggleGroup;

    private List<Long> sequence = new ArrayList<Long>();

    public void generateSequence() {
        sequence.clear();
        resultSequenceTextArea.clear();

        int from = UiUtils.getIntValue(generateFromTextField);
        int to = UiUtils.getIntValue(generateToTextField);

        Sequence<Long> function = FUNCTIONS_MAP.get(getSelectedFunction());

        if (function != null) {
            List<Long> sequence = function.generateSequence(from, to);
            resultSequenceTextArea.textProperty().setValue(sequence.toString());

            this.sequence = sequence;
        }
    }

    public long[] getSequence() {
        return ArrayUtils.toPrimitive(sequence.toArray(new Long[sequence.size()]));
    }

    public double[] getDoubleSequence() {
        long[] sequence = getSequence();
        double[] doubleSequence = new double[sequence.length];
        for (int i = 0; i < sequence.length; i++) {
            doubleSequence[i] = sequence[i];
        }
        return doubleSequence;
    }

    public int getPredictedAmount() {
        return UiUtils.getIntValue(predictedAmountTextField);
    }

    public void setPredictedSequence(double[] predictedSequence) {
        predictedSequenceTextArea.textProperty().setValue(Arrays.toString(predictedSequence));
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
        generateFromTextField.textProperty().addListener(new IntTextFieldConstraint(generateFromTextField));
        generateToTextField.textProperty().addListener(new IntTextFieldConstraint(generateToTextField));
        predictedAmountTextField.textProperty().addListener(new IntTextFieldConstraint(predictedAmountTextField));
    }

    private String getSelectedFunction() {
        return ((RadioButton) functionsToggleGroup.getSelectedToggle()).textProperty().getValue();
    }
}
