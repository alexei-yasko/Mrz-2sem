package yaskoam.mrz2.lab2.ui.panels;

import java.net.URL;
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
import yaskoam.mrz2.lab2.functions.NaturalSequence;
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

    private static final Map<String, Sequence<Integer>> FUNCTIONS_MAP = new HashMap<String, Sequence<Integer>>() {{
        put("Fibonacci", new FibonacciSequence());
        put("Natural", new NaturalSequence());
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
    private RadioButton naturalRadioButton;

    @FXML
    private RadioButton fibonacciRadioButton;

    @FXML
    private TextArea resultSequenceTextArea;

    @FXML
    public TextArea predictedSequenceTextArea;

    private ToggleGroup functionsToggleGroup;

    private int[] sequence;

    public void generateSequence() {
        sequence = new int[]{};
        resultSequenceTextArea.clear();

        int from = UiUtils.getIntValue(generateFromTextField);
        int to = UiUtils.getIntValue(generateToTextField);

        Sequence<Integer> function = FUNCTIONS_MAP.get(getSelectedFunction());

        if (function != null) {
            List<Integer> sequence = function.generateSequence(from, to);
            this.sequence = ArrayUtils.toPrimitive(sequence.toArray(new Integer[sequence.size()]));
            resultSequenceTextArea.textProperty().setValue(Arrays.toString(this.sequence));
        }
    }

    public double[] getDoubleSequence() {
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

        naturalRadioButton.textProperty().setValue("Natural");
        naturalRadioButton.setToggleGroup(functionsToggleGroup);

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
