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
    }

    public void learn() {
        List<File> files = createFileChooser().showOpenMultipleDialog(mainPanel.getScene().getWindow());
        for (File file : files) {
            learnSimple(file);
        }
        mainPanel.getSourceImagePanel().clear();
        JOptionPane.showMessageDialog(null, "Network successfully learned.", "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public void recognize() {
        if (neuralNetwork != null) {
            Image image = mainPanel.getSourceImagePanel().getImage();
            recognizedImages = neuralNetwork.recognize(image, 100);
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

    private void learnSimple(File file) {
        if (file != null) {
            mainPanel.getSourceImagePanel().setImage(ImageDecoder.fromFile(file));
        }
        if (neuralNetwork != null) {
            neuralNetwork.learn(mainPanel.getSourceImagePanel().getImage());
        }
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
