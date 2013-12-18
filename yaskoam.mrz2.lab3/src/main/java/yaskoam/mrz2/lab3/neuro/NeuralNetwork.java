package yaskoam.mrz2.lab3.neuro;

import java.util.ArrayList;
import java.util.List;

import org.jblas.FloatMatrix;

import yaskoam.mrz2.lab3.image.Image;

/**
 * @author Q-YAA
 */
public class NeuralNetwork {

    private FloatMatrix W;

    public NeuralNetwork(int width, int height) {
        W = FloatMatrix.zeros(width * height, width * height);
    }

    public boolean learn(Image[] images) {
        for (Image image : images) {
            learnImage(image);
        }
        return checkLearn(images);
    }

    public List<Image> recognize(Image image, int maxIterationsNumber) {
        List<Image> images = new ArrayList<Image>();

        Image recognizedImage;

        for (int i = 0; i < maxIterationsNumber; i++) {
            Image currentImage = images.size() > 0 ? images.get(images.size() - 1) : image;
            Image previousImage = images.size() > 2 ? images.get(images.size() - 2) : null;

            recognizedImage = recognizeImage(currentImage);

            if (currentImage.equals(recognizedImage) || (previousImage != null && previousImage.equals(recognizedImage))) {
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

        float[] S = X.transpose().mmul(W).data;

        int[] Y = new int[S.length];
        for (int i = 0; i < Y.length; i++) {
            Y[i] = (int) Math.signum(S[i]);
        }

        return Image.fromFlatten(Y, image.getWidth(), image.getHeight());
    }

    private void learnImage(Image image) {
        FloatMatrix X = new FloatMatrix(toFloatArray(image.flattenValues()));
        FloatMatrix first = (W.mmul(X).sub(X)).mmul(W.mmul(X).sub(X).transpose());
        FloatMatrix second = (X.transpose().mmul(X)).sub(X.transpose().mmul(W).mmul(X));
        W = W.add(first.div(second));

        for (int i = 0; i < W.columns; i++) {
            W.put(i, i, 0);
        }
    }

//    private void learnImage(Image image) {
//        int[] X = image.flattenValues();
//        for (int i = 0; i < X.length; i++) {
//            for (int j = 0; j < X.length; j++) {
//                W.put(i, j, i == j ? 0 : W.get(i, j) + X[i] * X[j]);
//            }
//        }
//    }

    private boolean checkLearn(Image[] images) {
        for (Image image : images) {
            Image recognizedImage = recognizeImage(image);
            if (!image.equals(recognizedImage)) {
                return false;
            }
        }
        return true;
    }

    private float[] toFloatArray(int[] intArray) {
        float[] floatArray = new float[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            floatArray[i] = intArray[i];
        }
        return floatArray;
    }
}
