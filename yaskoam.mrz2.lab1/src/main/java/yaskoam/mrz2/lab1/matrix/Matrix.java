package yaskoam.mrz2.lab1.matrix;

import com.google.common.primitives.Floats;

/**
 * @author Q-YAA
 */
public class Matrix {

    private float[][] elements;

    private int height;

    private int width;

    public Matrix(float[][] elements) {
        this.elements = elements;
        this.height = elements.length;
        this.width = elements[0].length;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public float get(int i, int j) {
        return elements[i][j];
    }

    public float[] getRow(int i) {
        return elements[i];
    }

    public float[][] getElements() {
        return elements;
    }

    public Matrix transpose() {

        float[][] transposedElements = new float[getWidth()][getHeight()];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                transposedElements[i][j] = elements[j][i];
            }
        }

        return new Matrix(transposedElements);
    }

    public Matrix multiply(Matrix matrix) {

        float[][] resultElements = new float[height][matrix.getWidth()];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                for (int k = 0; k < width; k++) {
                    resultElements[i][j] += elements[i][k] * matrix.get(k, j);
                }
            }
        }

        return new Matrix(resultElements);
    }

    public Matrix multiply(float value) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                elements[i][j] *= value;
            }
        }
        return this;
    }

    public Matrix subtract(Matrix matrix) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                elements[i][j] -= matrix.get(i, j);
            }
        }
        return this;
    }

    public Matrix add(Matrix matrix) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                elements[i][j] += matrix.get(i, j);
            }
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (float[] row : elements) {
            stringBuilder.append(Floats.join(" ", row));
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}





















