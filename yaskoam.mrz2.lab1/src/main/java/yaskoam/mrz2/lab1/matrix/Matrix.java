package yaskoam.mrz2.lab1.matrix;

import com.google.common.primitives.Doubles;

/**
 * @author Q-YAA
 */
public class Matrix {

    private double[][] elements;

    private int height;

    private int width;

    public Matrix(double[][] elements) {
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

    public double get(int i, int j) {
        return elements[i][j];
    }

    public double[] getRow(int i) {
        return elements[i];
    }

    public double[][] getElements() {
        return elements;
    }

    public Matrix transpose() {

        double[][] transposedElements = new double[getWidth()][getHeight()];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                transposedElements[i][j] = elements[j][i];
            }
        }

        return new Matrix(transposedElements);
    }

    public Matrix multiply(Matrix matrix) {

        double[][] resultElements = new double[height][matrix.getWidth()];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                for (int k = 0; k < width; k++) {
                    resultElements[i][j] += elements[i][k] * matrix.get(k, j);
                }
            }
        }

        return new Matrix(resultElements);
    }

    public Matrix multiply(double value) {
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

        for (double[] row : elements) {
            stringBuilder.append(Doubles.join(" ", row));
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}





















