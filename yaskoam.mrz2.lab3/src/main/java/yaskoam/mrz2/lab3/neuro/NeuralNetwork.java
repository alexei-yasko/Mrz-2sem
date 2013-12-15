package yaskoam.mrz2.lab3.neuro;

import java.util.ArrayList;
import java.util.List;

import org.jblas.FloatMatrix;

import com.google.common.collect.Lists;

import yaskoam.mrz2.lab3.image.Image;

/**
 * @author Q-YAA
 */
public class NeuralNetwork {

    private FloatMatrix weightMatrix;

    public NeuralNetwork(int width, int height) {
        weightMatrix = FloatMatrix.zeros(width * height, width * height);
    }

    public void learn(Image image) {
        int[] X = image.flattenValues();
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X.length; j++) {
                weightMatrix.put(i, j, i == j ? 0 : weightMatrix.get(i, j) + X[i] * X[j]);
            }
        }
    }

    public List<Image> recognize(Image image, int maxIterationsNumber) {
        List<Image> images = Lists.newArrayList(image);

        Image recognizedImage;

        for (int i = 0; i < maxIterationsNumber; i++) {
            Image currentImage = images.get(images.size() - 1);

            recognizedImage = recognizeImage(currentImage);

            if (currentImage.equals(recognizedImage)) {
                break;
            }
            else {
                images.add(recognizedImage);
            }
        }

        return images;
    }

    private Image recognizeImage(Image image) {
        FloatMatrix X = new FloatMatrix(toFloatArray(image.flattenValues()));

        float[] S = X.transpose().mmul(weightMatrix).data;

        int[] Y = new int[S.length];
        for (int i = 0; i < Y.length; i++) {
            Y[i] = (int) Math.signum(S[i]);
        }

        return Image.fromFlatten(Y, image.getWidth(), image.getHeight());
    }

    private float[] toFloatArray(int[] intArray) {
        float[] floatArray = new float[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            floatArray[i] = intArray[i];
        }
        return floatArray;
    }


//    public double[] predict(double[] sequence, int predictedAmount) {
//
//        double[] predictedSequence = new double[predictedAmount];
//
//        for (int i = 0; i < predictedAmount; i++) {
//
//            double[] image = new double[windowSize];
//
//            if (windowSize - i > 0) {
//                System.arraycopy(sequence, sequence.length - windowSize + i, image, 0, windowSize - i);
//                System.arraycopy(predictedSequence, 0, image, windowSize - i, i);
//            }
//            else {
//                System.arraycopy(predictedSequence, i - windowSize, image, 0, windowSize);
//            }
//
//            DoubleMatrix X = DoubleMatrix.concatVertically(new DoubleMatrix(image), contextNeurons);
//
//            DoubleMatrix Y1 = weightMatrix1.mmul(X);
//            DoubleMatrix Y2 = weightMatrix2.mmul(Y1);
//
//            predictedSequence[i] = Y2.data[0];
//        }
//
//        return predictedSequence;
//    }
//
//    public void learn(double[] sequence) {
//        DoubleMatrix[] learningMatrix = createLearningMatrix(sequence);
//        double[] etalons = createEtalons(sequence);
//
//        double totalError;
//        int iterations = 0;
//
//        do {
//
//            // learn
//            for (int i = 0; i < learningMatrix.length; i++) {
//
//                DoubleMatrix X = DoubleMatrix.concatVertically(learningMatrix[i], contextNeurons);
//
//                DoubleMatrix Xn = normalize(X);
//                double norm = X.norm2();
//
//                DoubleMatrix Y1 = weightMatrix1.mmul(Xn);
//                DoubleMatrix Y2 = weightMatrix2.mmul(Y1);
//
//                DoubleMatrix gamma2 = Y2.mul(norm).sub(etalons[i]);
//                DoubleMatrix gamma1 = gamma2.mmul(weightMatrix2);
//
//                weightMatrix1 = weightMatrix1.sub(Xn.mmul(gamma1.mul(learningCoefficient)));
//                weightMatrix2 = weightMatrix2.sub(Y1.mmul(gamma2.mul(learningCoefficient)));
//
//                contextNeurons = Y1;
//            }
//
//            totalError = 0;
//
//            // calculate total error
//            for (int i = 0; i < learningMatrix.length; i++) {
//
//                DoubleMatrix X = DoubleMatrix.concatVertically(learningMatrix[i], contextNeurons);
//
//                DoubleMatrix Xn = normalize(X);
//                double norm = X.norm2();
//
//                DoubleMatrix Y1 = weightMatrix1.mmul(Xn);
//                DoubleMatrix Y2 = weightMatrix2.mmul(Y1);
//
//                DoubleMatrix gamma2 = Y2.mul(norm).sub(etalons[i]);
//
//                totalError += pow(gamma2.data[0], 2);
//            }
//
//            iterations++;
//
//            logByStep(iterations, totalError, logStep);
//            makeDelay(delay);
//        }
//        while (totalError >= maxError && iterations <= maxIterations && !Thread.interrupted());
//
//        logger.log(totalError, iterations);
//    }
//
//    public void setLogger(Logger logger) {
//        this.logger = logger;
//    }
//
//    public void setDelay(int delay) {
//        this.delay = delay;
//    }
//
//    public void setLogStep(int logStep) {
//        this.logStep = logStep != 0 ? logStep : 1;
//    }
//
//    public DoubleMatrix getWeightMatrix1() {
//        return weightMatrix1;
//    }
//
//    public DoubleMatrix getWeightMatrix2() {
//        return weightMatrix2;
//    }
//
//    private double[] createEtalons(double[] sequence) {
//        return Arrays.copyOfRange(sequence, windowSize, windowSize + imagesNumber);
//    }
//
//    private DoubleMatrix normalize(DoubleMatrix vector) {
//
//        double normalizationValue = 0;
//        for (int i = 0; i < vector.length; i++) {
//            normalizationValue += pow(vector.get(i), 2);
//        }
//
//        if (normalizationValue != 0) {
//            DoubleMatrix normalizedVector = new DoubleMatrix(vector.length);
//
//            for (int i = 0; i < vector.length; i++) {
//                normalizedVector.data[i] = vector.get(i) / sqrt(normalizationValue);
//            }
//
//            return normalizedVector;
//        }
//        else {
//            return vector;
//        }
//    }
//
//    private DoubleMatrix[] createLearningMatrix(double[] sequence) {
//        DoubleMatrix[] learningMatrix = new DoubleMatrix[imagesNumber];
//        for (int i = 0; i < imagesNumber; i++) {
//            learningMatrix[i] = new DoubleMatrix(Arrays.copyOfRange(sequence, i, i + windowSize));
//        }
//        return learningMatrix;
//    }
//
//    private DoubleMatrix createRandomMatrix(int n, int m) {
//        double[][] array = new double[n][m];
//        Random random = new Random();
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < m; j++) {
//                array[i][j] = Math.random() * (random.nextBoolean() ? -1 : 1) * 0.01;
//            }
//        }
//        return new DoubleMatrix(array);
//    }
//
//    private void makeDelay(int delay) {
//        try {
//            Thread.sleep(delay);
//        }
//        catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    private void logByStep(int iterations, double totalError, int logStep) {
//        if (iterations % logStep == 0) {
//            logger.log(totalError, iterations);
//        }
//    }
}
