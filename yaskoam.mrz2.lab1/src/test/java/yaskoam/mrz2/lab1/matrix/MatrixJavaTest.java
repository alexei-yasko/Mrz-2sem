package yaskoam.mrz2.lab1.matrix;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Q-YAA
 */
public class MatrixJavaTest {

    @Test
    public void testTransposition() {
        Matrix matrix = new Matrix(new double[][] {
            new double[] { 1, 2, 3 },
            new double[] { 4, 5, 6 },
            new double[] { 7, 8, 9 },
            new double[] { 10, 11, 12 }
        });

        Matrix transposedMatrix = matrix.transpose();

        Assert.assertThat(transposedMatrix.get(0, 0), Is.is(matrix.get(0, 0)));
        Assert.assertThat(transposedMatrix.get(0, 1), Is.is(matrix.get(1, 0)));
        Assert.assertThat(transposedMatrix.get(0, 2), Is.is(matrix.get(2, 0)));
        Assert.assertThat(transposedMatrix.get(0, 3), Is.is(matrix.get(3, 0)));

        Assert.assertThat(transposedMatrix.get(1, 0), Is.is(matrix.get(0, 1)));
        Assert.assertThat(transposedMatrix.get(1, 1), Is.is(matrix.get(1, 1)));
        Assert.assertThat(transposedMatrix.get(1, 2), Is.is(matrix.get(2, 1)));
        Assert.assertThat(transposedMatrix.get(1, 3), Is.is(matrix.get(3, 1)));

        Assert.assertThat(transposedMatrix.get(2, 0), Is.is(matrix.get(0, 2)));
        Assert.assertThat(transposedMatrix.get(2, 1), Is.is(matrix.get(1, 2)));
        Assert.assertThat(transposedMatrix.get(2, 2), Is.is(matrix.get(2, 2)));
        Assert.assertThat(transposedMatrix.get(2, 3), Is.is(matrix.get(3, 2)));
    }

    @Test
    public void testMultiplication1() {
        Matrix matrix1 = new Matrix(new double[][] {
            new double[] { 1, 0, 3 }
        });

        Matrix matrix2 = new Matrix(new double[][] {
            new double[] { 2 },
            new double[] { -1 },
            new double[] { 1 }
        });

        Matrix resultMatrix = matrix1.multiply(matrix2);

        Assert.assertThat(resultMatrix.getHeight(), Is.is(1));
        Assert.assertThat(resultMatrix.getWidth(), Is.is(1));

        Assert.assertThat(resultMatrix.get(0, 0), Is.is(5d));
    }

    @Test
    public void testMultiplication2() {
        Matrix matrix1 = new Matrix(new double[][] {
            new double[] { 2, 3 },
            new double[] { 0, -2 },
            new double[] { -1, 4 }
        });

        Matrix matrix2 = new Matrix(new double[][] {
            new double[] { 1, -1, 0, 3 },
            new double[] { 2, 1, -2, -4 }
        });

        Matrix resultMatrix = matrix1.multiply(matrix2);

        Assert.assertThat(resultMatrix.getHeight(), Is.is(3));
        Assert.assertThat(resultMatrix.getWidth(), Is.is(4));

        Assert.assertThat(resultMatrix.get(0, 0), Is.is(8d));
        Assert.assertThat(resultMatrix.get(0, 1), Is.is(1d));
        Assert.assertThat(resultMatrix.get(0, 2), Is.is(-6d));
        Assert.assertThat(resultMatrix.get(0, 3), Is.is(-6d));

        Assert.assertThat(resultMatrix.get(1, 0), Is.is(-4d));
        Assert.assertThat(resultMatrix.get(1, 1), Is.is(-2d));
        Assert.assertThat(resultMatrix.get(1, 2), Is.is(4d));
        Assert.assertThat(resultMatrix.get(1, 3), Is.is(8d));

        Assert.assertThat(resultMatrix.get(2, 0), Is.is(7d));
        Assert.assertThat(resultMatrix.get(2, 1), Is.is(5d));
        Assert.assertThat(resultMatrix.get(2, 2), Is.is(-8d));
        Assert.assertThat(resultMatrix.get(2, 3), Is.is(-19d));
    }


    @Test
    public void testMultiplication3() {
        Matrix matrix1 = new Matrix(new double[][] {
            new double[] { 2, 3 },
            new double[] { 4, 5 },
            new double[] { 6, 7 }
        });

        Matrix resultMatrix = matrix1.multiply(2);

        Assert.assertThat(resultMatrix.getHeight(), Is.is(3));
        Assert.assertThat(resultMatrix.getWidth(), Is.is(2));

        Assert.assertThat(resultMatrix.get(0, 0), Is.is(4d));
        Assert.assertThat(resultMatrix.get(0, 1), Is.is(6d));

        Assert.assertThat(resultMatrix.get(1, 0), Is.is(8d));
        Assert.assertThat(resultMatrix.get(1, 1), Is.is(10d));

        Assert.assertThat(resultMatrix.get(2, 0), Is.is(12d));
        Assert.assertThat(resultMatrix.get(2, 1), Is.is(14d));
    }
}
