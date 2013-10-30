package yaskoam.mrz2.lab2.neuro;

import java.util.Random;

import org.jblas.DoubleMatrix;

import yaskoam.mrz2.lab2.DefaultLogger;
import yaskoam.mrz2.lab2.Logger;

/**
 * @author Q-YAA
 */
public class NeuralNetwork {

    private int windowSize;

    private int imagesNumber;

    private double learningCoefficient;

    private double maxError;

    private double maxIterations;

    private DoubleMatrix weightMatrix1;

    private DoubleMatrix weightMatrix2;

    private DoubleMatrix contextNeurons;

    private Logger logger = new DefaultLogger();

    private int delay = 0;

    private int logStep = 1;

    public NeuralNetwork(int windowSize, int imagesNumber, double learningCoefficient, double maxError, int maxIterations) {

        this.windowSize = windowSize;
        this.imagesNumber = imagesNumber;
        this.learningCoefficient = learningCoefficient;
        this.maxError = maxError;
        this.maxIterations = maxIterations;

        weightMatrix1 = new DoubleMatrix(createRandomArray(imagesNumber, windowSize + imagesNumber));
        weightMatrix2 = new DoubleMatrix(createRandomArray(1, imagesNumber));
        contextNeurons = new DoubleMatrix(new double[imagesNumber][1]);
    }

    public double[] predict(double[] sequence, int predictedAmount) {

        double[] predictedSequence = new double[predictedAmount];

        for (int i = 0; i < predictedAmount; i++) {
//            double[] image = System.arraycopy(sequence, );
        }

        return null;
    }

    public void learn(double[] sequence) {
        double[][] learningMatrix = createLearningMatrix(sequence);
        double[] etalons = createEtalons(sequence);

        double totalError;
        int iterations = 0;

        do {

            // learn
            for (int i = 0; i < learningMatrix.length; i++) {

                DoubleMatrix X = DoubleMatrix.concatVertically(
                    new DoubleMatrix(new double[][]{learningMatrix[i]}).transpose(), contextNeurons);

                DoubleMatrix Y1 = weightMatrix1.mmul(X);
                DoubleMatrix Y2 = weightMatrix2.mmul(Y1);

                DoubleMatrix gamma2 = Y2.sub(etalons[i]);
                DoubleMatrix gamma1 = gamma2.mmul(weightMatrix2);

                weightMatrix1 = weightMatrix1.sub(X.mmul(gamma1.mul(learningCoefficient)));
                weightMatrix2 = weightMatrix2.sub(Y1.mmul(gamma2.mul(learningCoefficient)));

                contextNeurons = Y1;
            }

            totalError = 0;

            // calculate total error
            for (int i = 0; i < learningMatrix.length; i++) {

                DoubleMatrix X = DoubleMatrix.concatVertically(
                    new DoubleMatrix(new double[][]{learningMatrix[i]}).transpose(), contextNeurons);

                DoubleMatrix Y1 = weightMatrix1.mmul(X);
                DoubleMatrix Y2 = weightMatrix2.mmul(Y1);

                DoubleMatrix gamma2 = Y2.sub(etalons[i]);

                totalError += gamma2.data[0] * gamma2.data[0];
            }

            iterations++;

            logByStep(iterations, totalError, logStep);
            makeDelay(delay);
        }
        while (totalError >= maxError && iterations <= maxIterations && !Thread.interrupted());

        logger.log(totalError, iterations);
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setLogStep(int logStep) {
        this.logStep = logStep != 0 ? logStep : 1;
    }

    public DoubleMatrix getWeightMatrix1() {
        return weightMatrix1;
    }

    public DoubleMatrix getWeightMatrix2() {
        return weightMatrix2;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public int getImagesNumber() {
        return imagesNumber;
    }

    private double[] createEtalons(double[] sequence) {
        double[] samples = new double[imagesNumber];
        System.arraycopy(sequence, windowSize, samples, 0, imagesNumber);
        return samples;
    }

    private double[][] createLearningMatrix(double[] sequence) {
        double[][] learningMatrix = new double[imagesNumber][windowSize];
        for (int i = 0; i < imagesNumber; i++) {
            System.arraycopy(sequence, i, learningMatrix[i], 0, windowSize);
        }

        return learningMatrix;
    }

    private double[][] createRandomArray(int n, int m) {
        double[][] array = new double[n][m];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                array[i][j] = Math.random() * (random.nextBoolean() ? -1 : 1) * 0.01;
            }
        }

        return array;
    }

    private void makeDelay(int delay) {
        try {
            Thread.sleep(delay);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void logByStep(int iterations, double totalError, int logStep) {
        if (iterations % logStep == 0) {
            logger.log(totalError, iterations);
        }
    }
}
