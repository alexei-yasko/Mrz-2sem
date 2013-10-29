package yaskoam.mrz2.lab2.ui.toolbar;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import yaskoam.mrz2.lab2.neuro.NeuralNetwork;
import yaskoam.mrz2.lab2.ui.BaseComponent;
import yaskoam.mrz2.lab2.ui.MainPanel;

/**
 * @author Q-YAA
 */
public class ToolBarPanel extends BaseComponent {

    @FXML
    private Button startLearnButton;

    @FXML
    private Button stopLearnButton;

    private MainPanel mainPanel;

    private Thread calculationThread;

    public ToolBarPanel() {
    }

    public void generateSequence(ActionEvent event) {
        mainPanel.getSequencePanel().generateSequence();
    }

    public void startLearn(ActionEvent event) {
        double[] sequence = mainPanel.getSequencePanel().getDoubleSequence();

        int windowSize = mainPanel.getSettingsAndResultsPanel().getWindowSize();
        int imagesNumber = mainPanel.getSettingsAndResultsPanel().getImagesNumber();
        double learningCoefficient = mainPanel.getSettingsAndResultsPanel().getLearningCoefficient();
        double maxError = mainPanel.getSettingsAndResultsPanel().getMaxError();
        int maxIter = mainPanel.getSettingsAndResultsPanel().getMaxIter();

        if (sequence.length > 1) {
            mainPanel.getErrorChartPanel().clearErrorChart();
            changeButtonsState();
            mainPanel.getSettingsAndResultsPanel().disableResultTextFields();

            calculationThread = new Thread(new LearnTask(
                sequence, windowSize, imagesNumber, learningCoefficient, maxError, maxIter));

            calculationThread.setDaemon(true);
            calculationThread.start();
        }
    }

    public void stopLearn(ActionEvent event) {
        if (calculationThread != null && calculationThread.getState() == Thread.State.RUNNABLE) {
            calculationThread.interrupt();
        }
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    private class LearnTask extends Task {

        private double[] sequence;

        private int windowSize;

        private int imagesNumber;

        private int secondLayerNeurons;

        private double learningCoefficient;

        private double maxError;

        private int maxIterations;

        private LearnTask(double[] sequence, int windowSize, int imagesNumber,
            double learningCoefficient, double maxError, int maxIterations) {

            this.sequence = sequence;
            this.windowSize = windowSize;
            this.imagesNumber = imagesNumber;
            this.learningCoefficient = learningCoefficient;
            this.maxError = maxError;
            this.maxIterations = maxIterations;
        }

        @Override
        protected Object call() throws Exception {

            final NeuralNetwork neuralNetwork =
                new NeuralNetwork(windowSize, imagesNumber, learningCoefficient, maxError, maxIterations);
            neuralNetwork.setLogger(mainPanel.getUiLogger());

            try {
//                neuralNetwork.learn(segments);
//                double[][] compressedSegments = neuralNetwork.compress(segments);
//                double[][] decompressedSegments = neuralNetwork.decompress(compressedSegments);
//
//                neuroImage.collectFromSegments(segmentHeight, segmentWidth, decompressedSegments);
//                mainPanel.getImagePanel().setResultImage(NeuroImage.toImage(neuroImage));
//
                return null;
            }
            finally {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
//                        changeButtonsState();
//                        mainPanel.getSettingsAndResultsPanel().enableResultTextFields();
//                        mainPanel.getWeightMatrixPanel().displayWeightMatrices(
//                            neuralNetwork.getWeightMatrix1(), neuralNetwork.getWeightMatrix2());
                    }
                });
            }
        }
    }

    private void changeButtonsState() {
        startLearnButton.setDisable(!startLearnButton.isDisable());
        stopLearnButton.setDisable(!stopLearnButton.isDisable());
    }
}
