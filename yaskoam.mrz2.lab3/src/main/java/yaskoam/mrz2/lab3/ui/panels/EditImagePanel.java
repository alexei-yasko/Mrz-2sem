package yaskoam.mrz2.lab3.ui.panels;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import yaskoam.mrz2.lab3.image.Image;
import yaskoam.mrz2.lab3.image.ImageDecoder;
import yaskoam.mrz2.lab3.ui.BaseComponent;

/**
 * @author Q-YAA
 */
public class EditImagePanel extends BaseComponent {

    @FXML
    private TextArea imageTextArea;

    private Image image;

    public EditImagePanel(Image image) {
        this.image = image;
        setImageSymbols();
    }

    public void setImageSymbols() {
        StringBuilder imageString = new StringBuilder();

        for (char[] imageLine : ImageDecoder.toSymbols(image)) {
            imageString.append(new String(imageLine)).append('\n');
        }

        imageTextArea.setText(imageString.toString());
    }

    public Image getImage() {
        return image;
    }

    public boolean isImageEditted() {
        return image != null;
    }

    public void save(ActionEvent event) {
        String text = imageTextArea.getText();

        String[] lines = text.trim().split("\n");
        image = ImageDecoder.fromLines(lines);
        ((Stage) getScene().getWindow()).close();
    }

    public void cancel(ActionEvent event) {
        image = null;
        ((Stage) getScene().getWindow()).close();
    }
}
