package yaskoam.mrz2.lab1.neuro;

import java.util.Calendar;

import org.jblas.FloatMatrix;
/**
 * @author Q-YAA
 */
public class NeuralNetwork {

    private int n;

    private int p;

    private float maxError;

    private float a;

    private FloatMatrix W1;

    private FloatMatrix W2;

    public NeuralNetwork(int n, int p, float maxError, float a) {
        this.n = n;
        this.p = p;
        this.maxError = maxError;
        this.a = a;

        W1 = new FloatMatrix(createRandomArray(n, p));
        W2 = new FloatMatrix(createRandomArray(p, n));
    }


    public float[][] compress(float[][] segments) {
        float[][] compressed = new float[segments.length][p];

        for (int i = 0; i < segments.length; i++) {
            FloatMatrix X = new FloatMatrix(new float[][]{segments[i]});
            compressed[i] = X.mmul(W1).data;
        }

        return compressed;
    }

    public float[][] decompress(float[][] segments) {
        float[][] decompressed = new float[segments.length][n];

        for (int i = 0; i < segments.length; i++) {
            FloatMatrix Y = new FloatMatrix(new float[][]{segments[i]});
            decompressed[i] = Y.mmul(W2).data;
        }

        return decompressed;
    }

    public void learn(float[][] segments) {

        float error;

        do {

            long currentTime = Calendar.getInstance().getTimeInMillis();

            // learn
            for (float[] segment : segments) {
                FloatMatrix X = new FloatMatrix(new float[][]{segment});

                FloatMatrix Y = X.mmul(W1);
                FloatMatrix deltaX = Y.mmul(W2).sub(X);

                W1 = W1.sub((X.transpose()).mmul(deltaX).mmul((W2).transpose()).mmul(a));
                W2 = W2.sub(Y.transpose().mmul(deltaX).mmul(a));
            }

            error = 0;

            // calculate error
            for (float[] segment : segments) {
                FloatMatrix X = new FloatMatrix(new float[][]{segment});
                FloatMatrix Y = X.mmul(W1);
                FloatMatrix deltaX = Y.mmul(W2).sub(X);

                for (int j = 0; j < deltaX.length; j++) {
                    error += deltaX.get(0, j) * deltaX.get(0, j);
                }
            }

            System.out.println(error + ", Time: " + (Calendar.getInstance().getTimeInMillis() - currentTime));

        }
        while (error > maxError);
    }

    private float[][] createRandomArray(int n, int m) {
        float[][] array = new float[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                array[i][j] = (float) Math.random();
            }
        }

        return array;
    }
}
