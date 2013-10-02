package yaskoam.mrz2.lab1.neuro;

import java.util.Random;

import org.jblas.DoubleMatrix;

import yaskoam.mrz2.lab1.DefaultLogger;
import yaskoam.mrz2.lab1.Logger;

/**
 * @author Q-YAA
 */
public class NeuralNetwork {

    private int n;

    private int p;

    private double a;

    private double maxError;

    private double maxIterations;

    private DoubleMatrix W1;

    private DoubleMatrix W2;

    private Logger logger = new DefaultLogger();

    public NeuralNetwork(int n, int p, double a, double maxError, int maxIterations) {
        this.n = n;
        this.p = p;
        this.a = a;
        this.maxError = maxError;
        this.maxIterations = maxIterations;

        W1 = new DoubleMatrix(createRandomArray(n, p));
        W2 = new DoubleMatrix(createRandomArray(p, n));
    }


    public double[][] compress(double[][] segments) {
        double[][] compressed = new double[segments.length][p];

        for (int i = 0; i < segments.length; i++) {
            DoubleMatrix X = new DoubleMatrix(new double[][]{segments[i]});
            compressed[i] = X.mmul(W1).data;
        }

        return compressed;
    }

    public double[][] decompress(double[][] segments) {
        double[][] decompressed = new double[segments.length][n];

        for (int i = 0; i < segments.length; i++) {
            DoubleMatrix Y = new DoubleMatrix(new double[][]{segments[i]});
            decompressed[i] = Y.mmul(W2).data;
        }

        return decompressed;
    }

    public void learn(double[][] segments) {

        double totalError;
        int iterations = 0;

        do {

            long currentTime = System.currentTimeMillis();

            // learn
            for (double[] segment : segments) {
                DoubleMatrix X = new DoubleMatrix(new double[][]{segment});

                DoubleMatrix Y = X.mmul(W1);
                DoubleMatrix deltaX = Y.mmul(W2).sub(X);

                W1 = W1.sub((X.transpose()).mmul(deltaX).mmul((W2).transpose()).mmul(a));
                W2 = W2.sub(Y.transpose().mmul(deltaX).mmul(a));
            }

            totalError = 0;

            // calculate total error
            for (double[] segment : segments) {
                DoubleMatrix X = new DoubleMatrix(new double[][]{segment});
                DoubleMatrix Y = X.mmul(W1);
                DoubleMatrix deltaX = Y.mmul(W2).sub(X);

                for (int j = 0; j < deltaX.length; j++) {
                    totalError += deltaX.get(0, j) * deltaX.get(0, j);
                }
            }

            iterations++;

            System.out.println("Time: " + (System.currentTimeMillis() - currentTime));
            logger.log(totalError, totalError / (segments.length * segments[0].length * 3), iterations);
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
