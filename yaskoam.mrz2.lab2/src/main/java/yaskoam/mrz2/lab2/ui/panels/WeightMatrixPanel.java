package yaskoam.mrz2.lab2.ui.panels;

import org.jblas.DoubleMatrix;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import yaskoam.mrz2.lab2.ui.BaseComponent;

/**
 * @author Q-YAA
 */
public class WeightMatrixPanel extends BaseComponent {

    @FXML
    private TextArea weightMatrix1TextArea;

    @FXML
    private TextArea weightMatrix2TextArea;

    public WeightMatrixPanel() {

    }

    public void displayWeightMatrices(DoubleMatrix weightMatrix1, DoubleMatrix weightMatrix2) {
        weightMatrix1TextArea.setText(formatWeightMatrixString(weightMatrix1));
        weightMatrix2TextArea.setText(formatWeightMatrixString(weightMatrix2));
    }

    private String formatWeightMatrixString(DoubleMatrix weightMatrix) {

        StringBuilder weightMatrixString = new StringBuilder(weightMatrix.toString("%.5f", "", "", " | ", "\n"));

        for (int i = 1; i < weightMatrixString.length() - 2; i++) {

            if (weightMatrixString.charAt(i) != '-'
                && weightMatrixString.charAt(i + 1) == '0' && weightMatrixString.charAt(i + 2) == '.') {

                weightMatrixString.insert(i + 1, " ");
                i++;
            }
        }

        return weightMatrixString.charAt(0) != '-' ?
            weightMatrixString.insert(0, " ").toString() : weightMatrixString.toString();
    }
}
