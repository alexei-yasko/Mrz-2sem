package yaskoam.mrz2.lab1.neuro;

import org.jblas.DoubleMatrix;
/**
 * @author Q-YAA
 */
public class NeuralNetwork {

    private int n;

    private int p;

    private double maxError;

    private double a;

    private DoubleMatrix W1;

    private DoubleMatrix W2;

    public NeuralNetwork(int n, int p, double maxError, double a) {
        this.n = n;
        this.p = p;
        this.maxError = maxError;
        this.a = a;

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

        double error;

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

            error = 0;

            // calculate error
            for (double[] segment : segments) {
                DoubleMatrix X = new DoubleMatrix(new double[][]{segment});
                DoubleMatrix Y = X.mmul(W1);
                DoubleMatrix deltaX = Y.mmul(W2).sub(X);

                for (int j = 0; j < deltaX.length; j++) {
                    error += deltaX.get(0, j) * deltaX.get(0, j);
                }
            }

            System.out.println(error + ", Time: " + (System.currentTimeMillis() - currentTime));

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
