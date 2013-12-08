package yaskoam.mrz2.lab3.ui.support;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * @author Q-YAA
 */
public class DoubleTextFieldConstraint implements ChangeListener<String> {

    private String PATTERN = "[0-9\\.]*";

    private TextField textField;

    public DoubleTextFieldConstraint(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void changed(ObservableValue<? extends String> value, String s, String s2) {

        if (!s2.matches(PATTERN)) {
            textField.setText(s);
        }
    }
}