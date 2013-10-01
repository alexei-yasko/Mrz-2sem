package yaskoam.mrz2.lab1.ui.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;

import org.apache.commons.io.IOUtils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
public class MainSceneController {

    @FXML
    private VBox rootPane = null;

    @FXML
    private MenuItem loadSourceImageMenuItem = null;

    @FXML
    private ImageView sourceImageView = null;

    @FXML
    private ImageView resultImageView = null;

    @FXML
    private Label imageHeightLabel = null;

    @FXML
    private Label imageWidthLabel = null;

    @FXML
    private TextField nTextField = null;

    @FXML
    private TextField mTextField = null;

    @FXML
    private TextField pTextField = null;

    @FXML
    private TextField aTextField = null;

    @FXML
    private TextField eTextField = null;

    private Thread calculationThread;

    public void loadSourceImage(ActionEvent event) {
        File file = createFileChooser().showOpenDialog(rootPane.getScene().getWindow());

        if (file != null) {
            Image image = getImage(file);
            sourceImageView.setImage(image);
            setImageViewsEventHandlers();
        }
    }

    public void compressSourceImage() {
        long currentTime = Calendar.getInstance().getTimeInMillis();

        final Image image = sourceImageView.getImage();

        final int n = Integer.parseInt(nTextField.getText());
        final int m = Integer.parseInt(mTextField.getText());
        final int p = Integer.parseInt(pTextField.getText());
        final float a = Float.parseFloat(aTextField.getText());
        final float e = Float.parseFloat(eTextField.getText());

        if (image != null) {

            calculationThread = new Thread(new Runnable() {
                public void run() {
                    NeuroImage neuroImage = NeuroImage.fromImage(image);
                    float[][] segments = neuroImage.splitIntoSegments(n, m);

                    NeuralNetwork neuralNetwork = new NeuralNetwork(n * m * 3, p, e, a);
                    neuralNetwork.learn(segments);
                    float[][] compressedSegments = neuralNetwork.compress(segments);
                    float[][] decompressedSegments = neuralNetwork.decompress(compressedSegments);

                    neuroImage.collectFromSegments(n, m, decompressedSegments);
                    resultImageView.setImage(NeuroImage.toImage(neuroImage));
                }
            });

            calculationThread.setDaemon(true);
            calculationThread.start();
        }

        System.out.println("Time: " + (Calendar.getInstance().getTimeInMillis() - currentTime));
    }

    public void closeMainWindow(ActionEvent event) {
        Platform.exit();
    }

    public void stop(ActionEvent event) {
        calculationThread.stop();
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
}
