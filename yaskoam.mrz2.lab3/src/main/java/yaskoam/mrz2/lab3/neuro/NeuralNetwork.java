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
}
