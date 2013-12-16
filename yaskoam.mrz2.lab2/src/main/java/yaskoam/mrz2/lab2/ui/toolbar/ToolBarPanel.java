package yaskoam.mrz2.lab2.ui.toolbar;

import javax.swing.JOptionPane;

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
    private Button predictButton;

    @FXML
    private Button startLearnButton;

    @FXML
    private Button stopLearnButton;

    private MainPanel mainPanel;

    private Thread calculationThread;

    private NeuralNetwork neuralNetwork;

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
        int delay = mainPanel.getSettingsAndResultsPanel().getDelay();
        int logStep = mainPanel.getSettingsAndResultsPanel().getLogStep();

        checkSequence(sequence, windowSize, imagesNumber);

        mainPanel.getErrorChartPanel().clearErrorChart();
        changeButtonsState();
        mainPanel.getSettingsAndResultsPanel().disableResultTextFields();

        calculationThread = new Thread(new LearnTask(
            sequence, windowSize, imagesNumber, learningCoefficient, maxError, maxIter, delay, logStep));
        calculationThread.setDaemon(true);
        calculationThread.start();
    }

    public void stopLearn(ActionEvent event) {
        if (calculationThread != null) {
            calculationThread.interrupt();
        }
    }

    public void predict(ActionEvent event) {
        int predictedAmount = mainPanel.getSequencePanel().getPredictedAmount();
        double[] sequence = mainPanel.getSequencePanel().getDoubleSequence();

        if (neuralNetwork != null) {
            double[] predictedSequence = neuralNetwork.predict(sequence, predictedAmount);
            mainPanel.getSequencePanel().setPredictedSequence(predictedSequence);
        }
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    private void checkSequence(double[] sequence, int windowSize, int imagesNumber) {
        if (sequence.length - windowSize < imagesNumber) {
            JOptionPane.showMessageDialog(null, "Wrong sequence size!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class LearnTask extends Task {

        private double[] sequence;

        private int windowSize;

        private int imagesNumber;

        private double learningCoefficient;

        private double maxError;

        private int maxIterations;

        private int delay;

        private int logStep;

        private LearnTask(double[] sequence, int windowSize, int imagesNumber,
            double learningCoefficient, double maxError, int maxIterations, int delay, int logStep) {

            this.sequence = sequence;
            this.windowSize = windowSize;
            this.imagesNumber = imagesNumber;
            this.learningCoefficient = learningCoefficient;
            this.maxError = maxError;
            this.maxIterations = maxIterations;
            this.delay = delay;
            this.logStep = logStep;
        }

        @Override
        protected Object call() throws Exception {

            neuralNetwork = new NeuralNetwork(windowSize, imagesNumber, learningCoefficient, maxError, maxIterations);
            neuralNetwork.setLogger(mainPanel.getUiLogger());
            neuralNetwork.setDelay(delay);
            neuralNetwork.setLogStep(logStep);

            try {
                neuralNetwork.learn(sequence);
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
        startLearnButton.setDisable(!startLearnButton.isDisable());
        stopLearnButton.setDisable(!stopLearnButton.isDisable());
        predictButton.setDisable(!predictButton.isDisable());
    }
}
