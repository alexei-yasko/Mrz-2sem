package yaskoam.mrz2.lab1.neuro;

import java.util.Random;

import org.jblas.DoubleMatrix;

import yaskoam.mrz2.lab1.DefaultLogger;
import yaskoam.mrz2.lab1.Logger;

/**
 * @author Q-YAA
 */
public class NeuralNetwork {

    private int segmentLength;

    private int secondLayerNeurons;

    private double learningCoefficient;

    private double maxError;

    private double maxIterations;

    private DoubleMatrix WEIGHT_MATRIX_1;

    private DoubleMatrix WEIGHT_MATRIX_2;

    private Logger logger = new DefaultLogger();

    public NeuralNetwork(
        int segmentLength, int secondLayerNeurons, double learningCoefficient, double maxError, int maxIterations) {

        this.segmentLength = segmentLength;
        this.secondLayerNeurons = secondLayerNeurons;
        this.learningCoefficient = learningCoefficient;
        this.maxError = maxError;
        this.maxIterations = maxIterations;

        WEIGHT_MATRIX_1 = new DoubleMatrix(createRandomArray(segmentLength, secondLayerNeurons));
        WEIGHT_MATRIX_2 = new DoubleMatrix(createRandomArray(secondLayerNeurons, segmentLength));
    }


    public double[][] compress(double[][] segments) {
        double[][] compressed = new double[segments.length][secondLayerNeurons];

        for (int i = 0; i < segments.length; i++) {
            DoubleMatrix X = new DoubleMatrix(new double[][]{segments[i]});
            compressed[i] = X.mmul(WEIGHT_MATRIX_1).data;
        }

        return compressed;
    }

    public double[][] decompress(double[][] segments) {
        double[][] decompressed = new double[segments.length][segmentLength];

        for (int i = 0; i < segments.length; i++) {
            DoubleMatrix Y = new DoubleMatrix(new double[][]{segments[i]});
            decompressed[i] = Y.mmul(WEIGHT_MATRIX_2).data;
        }

        return decompressed;
    }

    public void learn(double[][] segments) {

        double totalError;
        int iterations = 0;

        do {
            // learn
            for (double[] segment : segments) {
                DoubleMatrix X = new DoubleMatrix(new double[][]{segment});

                DoubleMatrix Y = X.mmul(WEIGHT_MATRIX_1);
                DoubleMatrix deltaX = Y.mmul(WEIGHT_MATRIX_2).sub(X);

                WEIGHT_MATRIX_1 = WEIGHT_MATRIX_1
                    .sub((X.transpose()).mmul(deltaX).mmul((WEIGHT_MATRIX_2).transpose()).mmul(learningCoefficient));

                WEIGHT_MATRIX_2 = WEIGHT_MATRIX_2.sub(Y.transpose().mmul(deltaX).mmul(learningCoefficient));
            }

            totalError = 0;

            // calculate total error
            for (double[] segment : segments) {
                DoubleMatrix X = new DoubleMatrix(new double[][]{segment});
                DoubleMatrix Y = X.mmul(WEIGHT_MATRIX_1);
                DoubleMatrix deltaX = Y.mmul(WEIGHT_MATRIX_2).sub(X);

                for (int j = 0; j < deltaX.length; j++) {
                    totalError += deltaX.get(0, j) * deltaX.get(0, j);
                }
            }

            iterations++;

            double meanError = totalError / (segments.length * segments[0].length * 3);

            logger.log(totalError, meanError, iterations);
        }
        while (totalError >= maxError && iterations <= maxIterations);
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private double[][] createRandomArray(int n, int m) {
        double[][] array = new double[n][m];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                array[i][j] = Math.random() * (random.nextBoolean() ? -1 : 1) * 0.1;
            }
        }

        return array;
    }
}
