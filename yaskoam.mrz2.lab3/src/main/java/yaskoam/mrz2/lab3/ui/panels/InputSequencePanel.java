package yaskoam.mrz2.lab3.ui.panels;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import yaskoam.mrz2.lab3.ui.BaseComponent;
import yaskoam.mrz2.lab3.ui.BaseComponent;

/**
 * @author Q-YAA
 */
public class InputSequencePanel extends BaseComponent {

    private int[] sequence;

    @FXML
    private TextArea inputSequenceTextArea;

    @FXML
    private Label errorLabel;

    public int[] getSequence() {
        return sequence;
    }

    public boolean isSequenceEntered() {
        return sequence != null;
    }

    public void save(ActionEvent event) {
        String text = inputSequenceTextArea.getText();

        try {
            sequence = parseSequence(text);
            ((Stage) getScene().getWindow()).close();
        }
        catch (NumberFormatException ex) {
            sequence = null;
            errorLabel.visibleProperty().setValue(true);
        }
    }

    public void cancel(ActionEvent event) {
        sequence = null;
        ((Stage) getScene().getWindow()).close();
    }

    private int[] parseSequence(String text) {

        String[] stringSequence = text.trim().split(",");

        int[] sequence = new int[stringSequence.length];

        for (int i = 0; i < stringSequence.length; i++) {
            sequence[i] = Integer.parseInt(stringSequence[i].trim());
        }

        return sequence;
    }
}
