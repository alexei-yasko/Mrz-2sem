package yaskoam.mrz2.lab2.ui.toolbar;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import yaskoam.mrz2.lab2.ui.BaseComponent;

/**
 * @author Q-YAA
 */
public class ToolBarPanel extends BaseComponent {

    @FXML
    private Button stopButton;

    @FXML
    private Button compressButton;

    public ToolBarPanel() {
    }

    public void stop(ActionEvent event) {
//        if (calculationThread != null && calculationThread.getState() == Thread.State.RUNNABLE) {
//            calculationThread.stop();
//        }
    }

    //    public void compressSourceImage() {
    //        final Image image = sourceImageView.getImage();
    //
    //        final int segmentHeight = Integer.parseInt(segmentHeightTextField.getText());
    //        final int segmentWidth = Integer.parseInt(segmentWidthTextField.getText());
    //        final int secondLayerNeurons = Integer.parseInt(secondLayerNeuronsTextField.getText());
    //        final double learningCoefficient = Double.parseDouble(learningCoefficientTextField.getText());
    //        final double maxError = Double.parseDouble(maxErrorTextField.getText());
    //        final int maxIterations = Integer.parseInt(maxIterTextField.getText());
    //
    //        if (image != null) {
    //
    //            clearTotalErrorChart();
    //            changeButtonsState();
    //            disableResultTextFields();
    //
    //            calculationThread = new Thread(new CompressImageTask(
    //                image, segmentHeight, segmentWidth, secondLayerNeurons, learningCoefficient, maxError, maxIterations));
    //
    //            calculationThread.setDaemon(true);
    //            calculationThread.start();
    //        }
    //    }

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
//            NeuroImage neuroImage = NeuroImage.fromImage(image);
//            double[][] segments = neuroImage.splitIntoSegments(segmentHeight, segmentWidth);
//
//            final NeuralNetwork neuralNetwork = new NeuralNetwork(
//                segmentHeight * segmentWidth * 3, secondLayerNeurons, learningCoefficient, maxError, maxIterations);
//            neuralNetwork.setLogger(uiLogger);
//
//            try {
//                neuralNetwork.learn(segments);
//                double[][] compressedSegments = neuralNetwork.compress(segments);
//                double[][] decompressedSegments = neuralNetwork.decompress(compressedSegments);
//
//                neuroImage.collectFromSegments(segmentHeight, segmentWidth, decompressedSegments);
//                resultImageView.setImage(NeuroImage.toImage(neuroImage));
//
//                return null;
//            }
//            finally {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        changeButtonsState();
//                        enableResultTextFields();
//                        displayWeightMatrices(neuralNetwork.getWeightMatrix1(), neuralNetwork.getWeightMatrix2());
//                    }
//                });
//            }

            return null;
        }
    }

    private void changeButtonsState() {
        compressButton.setDisable(!compressButton.isDisable());
        stopButton.setDisable(!stopButton.isDisable());
    }
}
