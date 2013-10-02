package yaskoam.mrz2.lab1.ui.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import yaskoam.mrz2.lab1.neuro.NeuralNetwork;
import yaskoam.mrz2.lab1.neuro.NeuroImage;

/**
 * @author Q-YAA
 */
public class MainSceneController implements Initializable {

    @FXML
    private VBox rootPane;

    @FXML
    private MenuItem loadSourceImageMenuItem;

    @FXML
    private ImageView sourceImageView;

    @FXML
    private ImageView resultImageView;

    @FXML
    private Label imageHeightLabel;

    @FXML
    private Label imageWidthLabel;

    @FXML
    private TextField nTextField;

    @FXML
    private TextField mTextField;

    @FXML
    private TextField pTextField;

    @FXML
    private TextField aTextField;

    @FXML
    private TextField eTextField;

    @FXML
    private TextField maxIterTextField;

    @FXML
    private Button stopButton;

    @FXML
    private Button compressButton;

    private Thread calculationThread;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        setImageViewsEventHandlers();
        setTextFieldsEventHandlers();
    }

    public void loadSourceImage(ActionEvent event) {
        File file = createFileChooser().showOpenDialog(rootPane.getScene().getWindow());

        if (file != null) {
            Image image = getImage(file);
            sourceImageView.setImage(image);
        }
    }

    public void compressSourceImage() {
        long currentTime = System.currentTimeMillis();

        changeButtonsState();

        final Image image = sourceImageView.getImage();

        final int n = Integer.parseInt(nTextField.getText());
        final int m = Integer.parseInt(mTextField.getText());
        final int p = Integer.parseInt(pTextField.getText());
        final double a = Double.parseDouble(aTextField.getText());
        final double maxError = Double.parseDouble(eTextField.getText());
        final int maxIterations = Integer.parseInt(maxIterTextField.getText());

        if (image != null) {

            calculationThread = new Thread(new Runnable() {
                public void run() {
                    NeuroImage neuroImage = NeuroImage.fromImage(image);
                    double[][] segments = neuroImage.splitIntoSegments(n, m);

                    NeuralNetwork neuralNetwork = new NeuralNetwork(n * m * 3, p, a, maxError, maxIterations);
                    neuralNetwork.learn(segments);
                    double[][] compressedSegments = neuralNetwork.compress(segments);
                    double[][] decompressedSegments = neuralNetwork.decompress(compressedSegments);

                    neuroImage.collectFromSegments(n, m, decompressedSegments);
                    resultImageView.setImage(NeuroImage.toImage(neuroImage));

                    changeButtonsState();
                }
            });

            calculationThread.setDaemon(true);
            calculationThread.start();
        }

        System.out.println("Time: " + (System.currentTimeMillis() - currentTime));
    }

    public void closeMainWindow(ActionEvent event) {
        Platform.exit();
    }

    public void stop(ActionEvent event) {
        try {
            if (calculationThread != null && calculationThread.getState() == Thread.State.RUNNABLE) {
                calculationThread.stop();
            }
        }
        finally {
            changeButtonsState();
        }
    }

    public void disableLoadImageMenuItem(Event event) {
        loadSourceImageMenuItem.setDisable(!((Tab) event.getSource()).isSelected());
    }

    private void chooseImage(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();

        if (imageView.getImage() != null) {
            imageView.scaleXProperty().setValue(1.1);
            imageView.scaleYProperty().setValue(1.1);

            imageHeightLabel.setText("image height: " + imageView.getImage().getHeight());
            imageWidthLabel.setText("image width:" + imageView.getImage().getWidth());
        }
    }

    private void unChooseImage(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();

        if (imageView.getImage() != null) {
            imageView.scaleXProperty().setValue(1);
            imageView.scaleYProperty().setValue(1);

            imageHeightLabel.setText("");
            imageWidthLabel.setText("");
        }
    }

    private Image getImage(File file) {
        FileInputStream imageStream = null;
        try {
            imageStream = new FileInputStream(file);
            return new Image(imageStream);
        }
        catch (FileNotFoundException e) {
            throw new IllegalStateException("can't read image file", e);
        }
        finally {
            IOUtils.closeQuietly(imageStream);
        }
    }

    private FileChooser createFileChooser() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose Image");
        fileChooser.setInitialDirectory(new File("yaskoam.mrz2.lab1/images/"));

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JPEG", "*.jpg"),
            new FileChooser.ExtensionFilter("BMP", "*.bmp"),
            new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        return fileChooser;
    }

    private void setImageViewsEventHandlers() {

        sourceImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                chooseImage(event);
            }
        });

        sourceImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                unChooseImage(event);
            }
        });

        resultImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                chooseImage(event);
            }
        });

        resultImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                unChooseImage(event);
            }
        });
    }

    private void setTextFieldsEventHandlers() {

        nTextField.textProperty().addListener(new IntTextFieldChangeListener(nTextField));
        mTextField.textProperty().addListener(new IntTextFieldChangeListener(mTextField));
        pTextField.textProperty().addListener(new IntTextFieldChangeListener(pTextField));
        maxIterTextField.textProperty().addListener(new IntTextFieldChangeListener(maxIterTextField));

        aTextField.textProperty().addListener(new DoubleTextFieldChangeListener(aTextField));
        eTextField.textProperty().addListener(new DoubleTextFieldChangeListener(eTextField));
    }

    private void changeButtonsState() {
        compressButton.setDisable(!compressButton.isDisable());
        stopButton.setDisable(!stopButton.isDisable());
    }

    private class IntTextFieldChangeListener implements ChangeListener<String> {

        private String PATTERN = "[0-9]*";

        private TextField textField;

        public IntTextFieldChangeListener(TextField textField) {
            this.textField = textField;
        }

        @Override
        public void changed(ObservableValue<? extends String> value, String s, String s2) {

            if (!s2.matches(PATTERN)) {
                textField.setText(s);
            }
        }
    }

    private class DoubleTextFieldChangeListener implements ChangeListener<String> {

        private String PATTERN = "[0-9\\.]*";

        private TextField textField;

        public DoubleTextFieldChangeListener(TextField textField) {
            this.textField = textField;
        }

        @Override
        public void changed(ObservableValue<? extends String> value, String s, String s2) {

            if (!s2.matches(PATTERN)) {
                textField.setText(s);
            }
        }
    }
}
