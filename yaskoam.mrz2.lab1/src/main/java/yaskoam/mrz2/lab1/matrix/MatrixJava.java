package yaskoam.mrz2.lab1.matrix;

/**
 * @author Q-YAA
 */
public class MatrixJava {

    private double[][] elements;

    private int height;

    private int width;

    public MatrixJava(double[][] elements) {
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

    public MatrixJava transpose() {

        double[][] transposedElements = new double[getWidth()][getHeight()];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                transposedElements[i][j] = elements[j][i];
            }
        }

        return new MatrixJava(transposedElements);
    }

    public MatrixJava multiply(MatrixJava matrix) {

        double[][] resultElements = new double[height][matrix.getWidth()];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                for (int k = 0; k < width; k++) {
                    resultElements[i][j] += get(i, k) * matrix.get(k, j);
                }
            }
        }

        return new MatrixJava(resultElements);
    }

    public MatrixJava multiply(double value) {

        double[][] resultElements = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                resultElements[i][j] = get(i, j) * value;
            }
        }

        return new MatrixJava(resultElements);
    }

    public MatrixJava substract(MatrixJava matrix) {

        double[][] resultElements = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                resultElements[i][j] = get(i, j) - matrix.get(i, j);
            }
        }

        return new MatrixJava(resultElements);
    }

    public MatrixJava add(MatrixJava matrix) {

        double[][] resultElements = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                resultElements[i][j] = get(i, j) + matrix.get(i, j);
            }
        }

        return new MatrixJava(resultElements);
    }
}





















