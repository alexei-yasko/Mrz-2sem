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
        MatrixJava matrix = new MatrixJava(new double[][] {
            new double[] { 1, 2, 3 },
            new double[] { 4, 5, 6 },
            new double[] { 7, 8, 9 },
            new double[] { 10, 11, 12 }
        });

        MatrixJava transposedMatrix = matrix.transpose();

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
        MatrixJava matrix1 = new MatrixJava(new double[][] {
            new double[] { 1, 0, 3 }
        });

        MatrixJava matrix2 = new MatrixJava(new double[][] {
            new double[] { 2 },
            new double[] { -1 },
            new double[] { 1 }
        });

        MatrixJava resultMatrix = matrix1.multiply(matrix2);

        Assert.assertThat(resultMatrix.getHeight(), Is.is(1));
        Assert.assertThat(resultMatrix.getWidth(), Is.is(1));

        Assert.assertThat(resultMatrix.get(0, 0), Is.is(5.0));
    }

    @Test
    public void testMultiplication2() {
        MatrixJava matrix1 = new MatrixJava(new double[][] {
            new double[] { 2, 3 },
            new double[] { 0, -2 },
            new double[] { -1, 4 }
        });

        MatrixJava matrix2 = new MatrixJava(new double[][] {
            new double[] { 1, -1, 0, 3 },
            new double[] { 2, 1, -2, -4 }
        });

        MatrixJava resultMatrix = matrix1.multiply(matrix2);

        Assert.assertThat(resultMatrix.getHeight(), Is.is(3));
        Assert.assertThat(resultMatrix.getWidth(), Is.is(4));

        Assert.assertThat(resultMatrix.get(0, 0), Is.is(8.0));
        Assert.assertThat(resultMatrix.get(0, 1), Is.is(1.0));
        Assert.assertThat(resultMatrix.get(0, 2), Is.is(-6.0));
        Assert.assertThat(resultMatrix.get(0, 3), Is.is(-6.0));

        Assert.assertThat(resultMatrix.get(1, 0), Is.is(-4.0));
        Assert.assertThat(resultMatrix.get(1, 1), Is.is(-2.0));
        Assert.assertThat(resultMatrix.get(1, 2), Is.is(4.0));
        Assert.assertThat(resultMatrix.get(1, 3), Is.is(8.0));

        Assert.assertThat(resultMatrix.get(2, 0), Is.is(7.0));
        Assert.assertThat(resultMatrix.get(2, 1), Is.is(5.0));
        Assert.assertThat(resultMatrix.get(2, 2), Is.is(-8.0));
        Assert.assertThat(resultMatrix.get(2, 3), Is.is(-19.0));
    }


    @Test
    public void testMultiplication3() {
        MatrixJava matrix1 = new MatrixJava(new double[][] {
            new double[] { 2, 3 },
            new double[] { 4, 5 },
            new double[] { 6, 7 }
        });

        MatrixJava resultMatrix = matrix1.multiply(2);

        Assert.assertThat(resultMatrix.getHeight(), Is.is(3));
        Assert.assertThat(resultMatrix.getWidth(), Is.is(2));

        Assert.assertThat(resultMatrix.get(0, 0), Is.is(4.0));
        Assert.assertThat(resultMatrix.get(0, 1), Is.is(6.0));

        Assert.assertThat(resultMatrix.get(1, 0), Is.is(8.0));
        Assert.assertThat(resultMatrix.get(1, 1), Is.is(10.0));

        Assert.assertThat(resultMatrix.get(2, 0), Is.is(12.0));
        Assert.assertThat(resultMatrix.get(2, 1), Is.is(14.0));
    }
}
