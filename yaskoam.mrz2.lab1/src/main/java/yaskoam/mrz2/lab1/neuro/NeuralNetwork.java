package yaskoam.mrz2.lab1.neuro;

import java.util.Calendar;

import yaskoam.mrz2.lab1.matrix.Matrix;

/**
 * @author Q-YAA
 */
public class NeuralNetwork {

    private int n;

    private int p;

    private double maxError;

    private double a;

    private Matrix W1;

    private Matrix W2;

    public NeuralNetwork(int n, int p, double maxError, double a) {
        this.n = n;
        this.p = p;
        this.maxError = maxError;
        this.a = a;

        W1 = new Matrix(createRandomArray(n, p));
        W2 = new Matrix(createRandomArray(p, n));
    }


    public double[][] compress(double[][] segments) {
        double[][] compressed = new double[segments.length][p];

        for (int i = 0; i < segments.length; i++) {
            Matrix X = new Matrix(new double[][]{segments[i]});
            compressed[i] = X.multiply(W1).getRow(0);
        }

        return compressed;
    }

    public double[][] decompress(double[][] segments) {
        double[][] decompressed = new double[segments.length][n];

        for (int i = 0; i < segments.length; i++) {
            Matrix Y = new Matrix(new double[][]{segments[i]});
            decompressed[i] = Y.multiply(W2).getRow(0);
        }

        return decompressed;
    }

    public void learn(double[][] segments) {

        double error;

        do {

            double currentTime = Calendar.getInstance().getTimeInMillis();

            // learn
            for (double[] segment : segments) {

                Matrix X = new Matrix(new double[][]{segment});

                Matrix Y = X.multiply(W1);
                Matrix deltaX = Y.multiply(W2).subtract(X);

                W1 = W1.subtract((X.transpose()).multiply(deltaX).multiply((W2).transpose()).multiply(a));
                W2 = W2.subtract(Y.transpose().multiply(deltaX).multiply(a));
            }

            error = 0;

            // calculate error
            for (double[] segment : segments) {
                Matrix X = new Matrix(new double[][]{segment});
                Matrix Y = X.multiply(W1);
                Matrix deltaX = Y.multiply(W2).subtract(X);

                for (int j = 0; j < deltaX.getWidth(); j++) {
                    error += deltaX.get(0, j) * deltaX.get(0, j);
                }
            }

            System.out.println(error + ", Time: " + (Calendar.getInstance().getTimeInMillis() - currentTime));

        }
        while (error > maxError);
    }

    private double[][] createRandomArray(int n, int m) {
        double[][] array = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                array[i][j] = Math.random();
            }
        }

        return array;
    }
}
