package yaskoam.mrz2.lab3.ui.toolbar;

import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import yaskoam.mrz2.lab3.image.Image;
import yaskoam.mrz2.lab3.image.ImageDecoder;
import yaskoam.mrz2.lab3.neuro.NeuralNetwork;
import yaskoam.mrz2.lab3.ui.BaseComponent;
import yaskoam.mrz2.lab3.ui.MainPanel;
import yaskoam.mrz2.lab3.ui.panels.EditImagePanel;

/**
 * @author Q-YAA
 */
public class ToolBarPanel extends BaseComponent {

    @FXML
    private TextField networkHeightTextField;

    @FXML
    private TextField networkWidthTextField;

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    private MainPanel mainPanel;

    private NeuralNetwork neuralNetwork;

    private List<Image> recognizedImages;

    int curentImageNumber = -1;

    public void loadImage() {
        File file = createFileChooser().showOpenDialog(mainPanel.getScene().getWindow());
        if (file != null) {
            mainPanel.getSourceImagePanel().setImage(ImageDecoder.fromFile(file));
        }
    }

    public void editImage() {
        Stage stage = new Stage();
        EditImagePanel editImagePanel = new EditImagePanel(mainPanel.getSourceImagePanel().getImage());
        stage.setScene(new Scene(editImagePanel));
        stage.setTitle("Edit image");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(getScene().getWindow());
        stage.showAndWait();

        if (editImagePanel.isImageEditted()) {
            mainPanel.getSourceImagePanel().setImage(editImagePanel.getImage());
        }
    }

    public void createNetwork() {
        neuralNetwork = new NeuralNetwork(
            Integer.parseInt(networkWidthTextField.getText()), Integer.parseInt(networkHeightTextField.getText()));

        JOptionPane.showMessageDialog(null, "Network created.", "Message", JOptionPane.INFORMATION_MESSAGE);
        mainPanel.getSourceImagePanel().clear();
        mainPanel.getResultImagePanel().clear();
    }

    public void learn() {
        List<File> files = createFileChooser().showOpenMultipleDialog(mainPanel.getScene().getWindow());
        boolean isSuccessful = false;

        if (files != null) {
            Image[] images = new Image[files.size()];
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageDecoder.fromFile(files.get(i));
            }
            isSuccessful = neuralNetwork.learn(images);
        }

        if (isSuccessful) {
            JOptionPane.showMessageDialog(null, "Network successfully learned.", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Network learn failed.", "Message", JOptionPane.ERROR_MESSAGE);
        }

        mainPanel.getSourceImagePanel().clear();
    }

    public void recognize() {
        if (neuralNetwork != null) {
            Image image = mainPanel.getSourceImagePanel().getImage();
            recognizedImages = neuralNetwork.recognize(image, 1000);
            recognizedImages.add(0, image);
            curentImageNumber = recognizedImages.size() - 1;
            mainPanel.getResultImagePanel().setImage(recognizedImages.get(curentImageNumber));
            updateNextPreviousButtonState();
        }
    }

    public void previous() {
        curentImageNumber--;
        mainPanel.getResultImagePanel().setImage(recognizedImages.get(curentImageNumber));
        updateNextPreviousButtonState();
    }

    public void next() {
        curentImageNumber++;
        mainPanel.getResultImagePanel().setImage(recognizedImages.get(curentImageNumber));
        updateNextPreviousButtonState();
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    private void updateNextPreviousButtonState() {
        if (curentImageNumber <= 0) {
            previousButton.setDisable(true);
        }
        else {
            previousButton.setDisable(false);
        }

        if (curentImageNumber >= recognizedImages.size() - 1) {
            nextButton.setDisable(true);
        }
        else {
            nextButton.setDisable(false);
        }
    }

    private FileChooser createFileChooser() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose Image");
        fileChooser.setInitialDirectory(new File("yaskoam.mrz2.lab3/images/"));

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image files", "*.img")
        );

        return fileChooser;
    }
}
