package yaskoam.mrz2.lab1.ui.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;
import org.jblas.DoubleMatrix;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    private ImageView sourceImageView;

    @FXML
    private ImageView resultImageView;

    @FXML
    private Label imageHeightLabel;

    @FXML
    private Label imageWidthLabel;

    @FXML
    private TextField segmentHeightTextField;

    @FXML
    private TextField segmentWidthTextField;

    @FXML
    private TextField secondLayerNeuronsTextField;

    @FXML
    private TextField learningCoefficientTextField;

    @FXML
    private TextField maxErrorTextField;

    @FXML
    private TextField maxIterTextField;

    @FXML
    private TextField totalErrorTextField;

    @FXML
    private TextField meanErrorTextField;

    @FXML
    private TextField numberOfIterationsTextField;

    @FXML
    private Button stopButton;

    @FXML
    private Button compressButton;

    @FXML
    private TextArea weightMatrix1TextArea;

    @FXML
    private TextArea weightMatrix2TextArea;

    @FXML
    private LineChart totalErrorChart;

    private Thread calculationThread;

    private UiLogger uiLogger;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        uiLogger = createUiLogger();

        setImageViewsEventHandlers();
        setTextFieldsEventHandlers();
        initTotalErrorChart();
    }

    public void loadSourceImage(ActionEvent event) {
        File file = createFileChooser().showOpenDialog(rootPane.getScene().getWindow());

        if (file != null) {
            Image image = getImage(file);
            sourceImageView.setImage(image);
        }
    }

    public void compressSourceImage() {
        final Image image = sourceImageView.getImage();

        final int segmentHeight = Integer.parseInt(segmentHeightTextField.getText());
        final int segmentWidth = Integer.parseInt(segmentWidthTextField.getText());
        final int secondLayerNeurons = Integer.parseInt(secondLayerNeuronsTextField.getText());
        final double learningCoefficient = Double.parseDouble(learningCoefficientTextField.getText());
        final double maxError = Double.parseDouble(maxErrorTextField.getText());
        final int maxIterations = Integer.parseInt(maxIterTextField.getText());

        if (image != null) {

            clearTotalErrorChart();
            changeButtonsState();
            disableResultTextFields();

            calculationThread = new Thread(new CompressImageTask(
                image, segmentHeight, segmentWidth, secondLayerNeurons, learningCoefficient, maxError, maxIterations));

            calculationThread.setDaemon(true);
            calculationThread.start();
        }
    }

    public void closeMainWindow(ActionEvent event) {
        Platform.exit();
    }

    public void stop(ActionEvent event) {
        if (calculationThread != null && calculationThread.getState() == Thread.State.RUNNABLE) {
            calculationThread.stop();
        }
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

    private UiLogger createUiLogger() {
        StringProperty totalErrorProperty = new SimpleStringProperty();
        StringProperty meanErrorProperty = new SimpleStringProperty();
        StringProperty numberOfIterationsProperty = new SimpleStringProperty();

        totalErrorTextField.textProperty().bindBidirectional(totalErrorProperty);
        meanErrorTextField.textProperty().bindBidirectional(meanErrorProperty);
        numberOfIterationsTextField.textProperty().bindBidirectional(numberOfIterationsProperty);

        return new UiLogger(totalErrorProperty, meanErrorProperty, numberOfIterationsProperty);
    }

    private void initTotalErrorChart() {
        ObservableList<XYChart.Data<Number, Number>> chartPoints = FXCollections.observableArrayList();
        XYChart.Series<Number, Number> totalErrorSeries = new XYChart.Series<Number, Number>(chartPoints);
        totalErrorChart.getData().add(totalErrorSeries);

        uiLogger.setChartPoints(chartPoints);

        totalErrorChart.getXAxis().setLabel("Iterations");
        totalErrorChart.getYAxis().setLabel("Total error");
    }

    private void clearTotalErrorChart() {
        XYChart.Series totalErrorSeries = (XYChart.Series) totalErrorChart.getData().get(0);
        totalErrorSeries.getData().clear();
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
        segmentHeightTextField.textProperty().addListener(new IntTextFieldChangeListener(segmentHeightTextField));
        segmentWidthTextField.textProperty().addListener(new IntTextFieldChangeListener(segmentWidthTextField));
        secondLayerNeuronsTextField.textProperty().addListener(new IntTextFieldChangeListener(secondLayerNeuronsTextField));
        maxIterTextField.textProperty().addListener(new IntTextFieldChangeListener(maxIterTextField));

        learningCoefficientTextField.textProperty().addListener(new DoubleTextFieldChangeListener(learningCoefficientTextField));
        maxErrorTextField.textProperty().addListener(new DoubleTextFieldChangeListener(maxErrorTextField));
    }

    private void changeButtonsState() {
        compressButton.setDisable(!compressButton.isDisable());
        stopButton.setDisable(!stopButton.isDisable());
    }

    private void disableResultTextFields() {
        totalErrorTextField.setDisable(true);
        meanErrorTextField.setDisable(true);
        numberOfIterationsTextField.setDisable(true);
    }

    private void enableResultTextFields() {
        totalErrorTextField.setDisable(false);
        meanErrorTextField.setDisable(false);
        numberOfIterationsTextField.setDisable(false);
    }

    private class CompressImageTask extends Task {

        private Image image;

        private int segmentHeight;

        private int segmentWidth;

        private int secondLayerNeurons;

        private double learningCoefficient;

        private double maxError;

        private int maxIterations;

        private CompressImageTask(Image image, int segmentHeight, int segmentWidth, int secondLayerNeurons,
            double learningCoefficient, double maxError, int maxIterations) {

            this.image = image;
            this.segmentHeight = segmentHeight;
            this.segmentWidth = segmentWidth;
            this.secondLayerNeurons = secondLayerNeurons;
            this.learningCoefficient = learningCoefficient;
            this.maxError = maxError;
            this.maxIterations = maxIterations;
        }

        @Override
        protected Object call() throws Exception {
            NeuroImage neuroImage = NeuroImage.fromImage(image);
            double[][] segments = neuroImage.splitIntoSegments(segmentHeight, segmentWidth);

            final NeuralNetwork neuralNetwork = new NeuralNetwork(
                segmentHeight * segmentWidth * 3, secondLayerNeurons, learningCoefficient, maxError, maxIterations);
            neuralNetwork.setLogger(uiLogger);

            try {
                neuralNetwork.learn(segments);
                double[][] compressedSegments = neuralNetwork.compress(segments);
                double[][] decompressedSegments = neuralNetwork.decompress(compressedSegments);

                neuroImage.collectFromSegments(segmentHeight, segmentWidth, decompressedSegments);
                resultImageView.setImage(NeuroImage.toImage(neuroImage));

                return null;
            }
            finally {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        changeButtonsState();
                        enableResultTextFields();
                        displayWeightMatrices(neuralNetwork.getWeightMatrix1(), neuralNetwork.getWeightMatrix2());
                    }
                });
            }
        }
    }

    private void displayWeightMatrices(DoubleMatrix weightMatrix1, DoubleMatrix weightMatrix2) {
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
