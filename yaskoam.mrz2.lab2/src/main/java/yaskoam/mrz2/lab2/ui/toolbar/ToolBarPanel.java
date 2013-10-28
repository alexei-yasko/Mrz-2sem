package yaskoam.mrz2.lab2.ui.toolbar;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import yaskoam.mrz2.lab2.Settings;
import yaskoam.mrz2.lab2.neuro.NeuralNetwork;
import yaskoam.mrz2.lab2.neuro.NeuroImage;
import yaskoam.mrz2.lab2.ui.BaseComponent;
import yaskoam.mrz2.lab2.ui.MainPanel;

/**
 * @author Q-YAA
 */
public class ToolBarPanel extends BaseComponent {

    @FXML
    private Button stopButton;

    @FXML
    private Button compressButton;

    private MainPanel mainPanel;

    private Thread calculationThread;

    public ToolBarPanel() {
    }

    public void generateSequence(ActionEvent event) {
        mainPanel.getSequencePanel().generateSequence();
    }

    public void stop(ActionEvent event) {
        if (calculationThread != null && calculationThread.getState() == Thread.State.RUNNABLE) {
            calculationThread.stop();
        }
    }

    public void compressSourceImage() {
        mainPanel.getSettingsAndResultsPanel().updateSettings();
        final Image image = mainPanel.getImagePanel().getSourceImage();

        final int segmentHeight = Settings.get().getSegmentHeight();
        final int segmentWidth = Settings.get().getSegmentWidth();
        final int secondLayerNeurons = Settings.get().getSecondLayerNeurons();
        final double learningCoefficient = Settings.get().getLearningCoefficient();
        final double maxError = Settings.get().getMaxError();
        final int maxIterations = Settings.get().getMaxIterations();

        if (image != null) {

            mainPanel.getErrorChartPanel().clearErrorChart();
            changeButtonsState();
            mainPanel.getSettingsAndResultsPanel().disableResultTextFields();

            calculationThread = new Thread(new CompressImageTask(
                image, segmentHeight, segmentWidth, secondLayerNeurons, learningCoefficient, maxError, maxIterations));

            calculationThread.setDaemon(true);
            calculationThread.start();
        }
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
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
            neuralNetwork.setLogger(mainPanel.getUiLogger());

            try {
                neuralNetwork.learn(segments);
                double[][] compressedSegments = neuralNetwork.compress(segments);
                double[][] decompressedSegments = neuralNetwork.decompress(compressedSegments);

                neuroImage.collectFromSegments(segmentHeight, segmentWidth, decompressedSegments);
                mainPanel.getImagePanel().setResultImage(NeuroImage.toImage(neuroImage));

                return null;
            }
            finally {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        changeButtonsState();
                        mainPanel.getSettingsAndResultsPanel().enableResultTextFields();
                        mainPanel.getWeightMatrixPanel().displayWeightMatrices(
                            neuralNetwork.getWeightMatrix1(), neuralNetwork.getWeightMatrix2());
                    }
                });
            }
        }
    }

    private void changeButtonsState() {
        compressButton.setDisable(!compressButton.isDisable());
        stopButton.setDisable(!stopButton.isDisable());
    }
}
