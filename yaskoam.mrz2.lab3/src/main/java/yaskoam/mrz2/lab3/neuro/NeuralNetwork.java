package yaskoam.mrz2.lab3.neuro;

import java.util.Arrays;
import java.util.Random;

import org.jblas.DoubleMatrix;

import yaskoam.mrz2.lab3.DefaultLogger;
import yaskoam.mrz2.lab3.Logger;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

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

        weightMatrix1 = createRandomMatrix(imagesNumber, windowSize + imagesNumber);
        weightMatrix2 = createRandomMatrix(1, imagesNumber);
        contextNeurons = new DoubleMatrix(new double[imagesNumber][1]);
    }

    public double[] predict(double[] sequence, int predictedAmount) {

        double[] predictedSequence = new double[predictedAmount];

        for (int i = 0; i < predictedAmount; i++) {

            double[] image = new double[windowSize];

            if (windowSize - i > 0) {
                System.arraycopy(sequence, sequence.length - windowSize + i, image, 0, windowSize - i);
                System.arraycopy(predictedSequence, 0, image, windowSize - i, i);
            }
            else {
                System.arraycopy(predictedSequence, i - windowSize, image, 0, windowSize);
            }

            DoubleMatrix X = DoubleMatrix.concatVertically(new DoubleMatrix(image), contextNeurons);

            DoubleMatrix Y1 = weightMatrix1.mmul(X);
            DoubleMatrix Y2 = weightMatrix2.mmul(Y1);

            predictedSequence[i] = Y2.data[0];
        }

        return predictedSequence;
    }

    public void learn(double[] sequence) {
        DoubleMatrix[] learningMatrix = createLearningMatrix(sequence);
        double[] etalons = createEtalons(sequence);

        double totalError;
        int iterations = 0;

        do {

            // learn
            for (int i = 0; i < learningMatrix.length; i++) {

                DoubleMatrix X = DoubleMatrix.concatVertically(learningMatrix[i], contextNeurons);

                DoubleMatrix Xn = normalize(X);
                double norm = X.norm2();

                DoubleMatrix Y1 = weightMatrix1.mmul(Xn);
                DoubleMatrix Y2 = weightMatrix2.mmul(Y1);

                DoubleMatrix gamma2 = Y2.mul(norm).sub(etalons[i]);
                DoubleMatrix gamma1 = gamma2.mmul(weightMatrix2);

                weightMatrix1 = weightMatrix1.sub(Xn.mmul(gamma1.mul(learningCoefficient)));
                weightMatrix2 = weightMatrix2.sub(Y1.mmul(gamma2.mul(learningCoefficient)));

                contextNeurons = Y1;
            }

            totalError = 0;

            // calculate total error
            for (int i = 0; i < learningMatrix.length; i++) {

                DoubleMatrix X = DoubleMatrix.concatVertically(learningMatrix[i], contextNeurons);

                DoubleMatrix Xn = normalize(X);
                double norm = X.norm2();

                DoubleMatrix Y1 = weightMatrix1.mmul(Xn);
                DoubleMatrix Y2 = weightMatrix2.mmul(Y1);

                DoubleMatrix gamma2 = Y2.mul(norm).sub(etalons[i]);

                totalError += pow(gamma2.data[0], 2);
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

    private double[] createEtalons(double[] sequence) {
        return Arrays.copyOfRange(sequence, windowSize, windowSize + imagesNumber);
    }

    private DoubleMatrix normalize(DoubleMatrix vector) {

        double normalizationValue = 0;
        for (int i = 0; i < vector.length; i++) {
            normalizationValue += pow(vector.get(i), 2);
        }

        if (normalizationValue != 0) {
            DoubleMatrix normalizedVector = new DoubleMatrix(vector.length);

            for (int i = 0; i < vector.length; i++) {
                normalizedVector.data[i] = vector.get(i) / sqrt(normalizationValue);
            }

            return normalizedVector;
        }
        else {
            return vector;
        }
    }

    private DoubleMatrix[] createLearningMatrix(double[] sequence) {
        DoubleMatrix[] learningMatrix = new DoubleMatrix[imagesNumber];
        for (int i = 0; i < imagesNumber; i++) {
            learningMatrix[i] = new DoubleMatrix(Arrays.copyOfRange(sequence, i, i + windowSize));
        }
        return learningMatrix;
    }

    private DoubleMatrix createRandomMatrix(int n, int m) {
        double[][] array = new double[n][m];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                array[i][j] = Math.random() * (random.nextBoolean() ? -1 : 1) * 0.01;
            }
        }
        return new DoubleMatrix(array);
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
