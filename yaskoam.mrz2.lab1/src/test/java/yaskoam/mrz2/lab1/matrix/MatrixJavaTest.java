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
        Matrix matrix = new Matrix(new float[][] {
            new float[] { 1, 2, 3 },
            new float[] { 4, 5, 6 },
            new float[] { 7, 8, 9 },
            new float[] { 10, 11, 12 }
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
        Matrix matrix1 = new Matrix(new float[][] {
            new float[] { 1, 0, 3 }
        });

        Matrix matrix2 = new Matrix(new float[][] {
            new float[] { 2 },
            new float[] { -1 },
            new float[] { 1 }
        });

        Matrix resultMatrix = matrix1.multiply(matrix2);

        Assert.assertThat(resultMatrix.getHeight(), Is.is(1));
        Assert.assertThat(resultMatrix.getWidth(), Is.is(1));

        Assert.assertThat(resultMatrix.get(0, 0), Is.is(5f));
    }

    @Test
    public void testMultiplication2() {
        Matrix matrix1 = new Matrix(new float[][] {
            new float[] { 2, 3 },
            new float[] { 0, -2 },
            new float[] { -1, 4 }
        });

        Matrix matrix2 = new Matrix(new float[][] {
            new float[] { 1, -1, 0, 3 },
            new float[] { 2, 1, -2, -4 }
        });

        Matrix resultMatrix = matrix1.multiply(matrix2);

        Assert.assertThat(resultMatrix.getHeight(), Is.is(3));
        Assert.assertThat(resultMatrix.getWidth(), Is.is(4));

        Assert.assertThat(resultMatrix.get(0, 0), Is.is(8f));
        Assert.assertThat(resultMatrix.get(0, 1), Is.is(1f));
        Assert.assertThat(resultMatrix.get(0, 2), Is.is(-6f));
        Assert.assertThat(resultMatrix.get(0, 3), Is.is(-6f));

        Assert.assertThat(resultMatrix.get(1, 0), Is.is(-4f));
        Assert.assertThat(resultMatrix.get(1, 1), Is.is(-2f));
        Assert.assertThat(resultMatrix.get(1, 2), Is.is(4f));
        Assert.assertThat(resultMatrix.get(1, 3), Is.is(8f));

        Assert.assertThat(resultMatrix.get(2, 0), Is.is(7f));
        Assert.assertThat(resultMatrix.get(2, 1), Is.is(5f));
        Assert.assertThat(resultMatrix.get(2, 2), Is.is(-8f));
        Assert.assertThat(resultMatrix.get(2, 3), Is.is(-19f));
    }


    @Test
    public void testMultiplication3() {
        Matrix matrix1 = new Matrix(new float[][] {
            new float[] { 2, 3 },
            new float[] { 4, 5 },
            new float[] { 6, 7 }
        });

        Matrix resultMatrix = matrix1.multiply(2);

        Assert.assertThat(resultMatrix.getHeight(), Is.is(3));
        Assert.assertThat(resultMatrix.getWidth(), Is.is(2));

        Assert.assertThat(resultMatrix.get(0, 0), Is.is(4f));
        Assert.assertThat(resultMatrix.get(0, 1), Is.is(6f));

        Assert.assertThat(resultMatrix.get(1, 0), Is.is(8f));
        Assert.assertThat(resultMatrix.get(1, 1), Is.is(10f));

        Assert.assertThat(resultMatrix.get(2, 0), Is.is(12f));
        Assert.assertThat(resultMatrix.get(2, 1), Is.is(14f));
    }
}
