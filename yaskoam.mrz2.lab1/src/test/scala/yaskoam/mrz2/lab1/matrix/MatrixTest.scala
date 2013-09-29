package yaskoam.mrz2.lab1.matrix

import org.junit.{Assert, Test}
import scala.collection.parallel.mutable.ParArray
import org.hamcrest.core.Is

/**
 * @author Q-YAA
 */
class MatrixTest {

  @Test
  def testTransposition() {

    val matrix = new Matrix(ParArray.tabulate(4)(i => ParArray.tabulate(3)(j => i * 3 + j)))

    val transposedMatrix = matrix.transpose

    Assert.assertThat(transposedMatrix.get(0, 0), Is.is(matrix.get(0, 0)))
    Assert.assertThat(transposedMatrix.get(0, 1), Is.is(matrix.get(1, 0)))
    Assert.assertThat(transposedMatrix.get(0, 2), Is.is(matrix.get(2, 0)))
    Assert.assertThat(transposedMatrix.get(0, 3), Is.is(matrix.get(3, 0)))

    Assert.assertThat(transposedMatrix.get(1, 0), Is.is(matrix.get(0, 1)))
    Assert.assertThat(transposedMatrix.get(1, 1), Is.is(matrix.get(1, 1)))
    Assert.assertThat(transposedMatrix.get(1, 2), Is.is(matrix.get(2, 1)))
    Assert.assertThat(transposedMatrix.get(1, 3), Is.is(matrix.get(3, 1)))

    Assert.assertThat(transposedMatrix.get(2, 0), Is.is(matrix.get(0, 2)))
    Assert.assertThat(transposedMatrix.get(2, 1), Is.is(matrix.get(1, 2)))
    Assert.assertThat(transposedMatrix.get(2, 2), Is.is(matrix.get(2, 2)))
    Assert.assertThat(transposedMatrix.get(2, 3), Is.is(matrix.get(3, 2)))
  }

  @Test
  def testMultiplication1() {
    val matrix1 = new Matrix(ParArray[ParArray[Double]](
      ParArray[Double](1.0, 0.0, 3.0)
    ))

    val matrix2 = new Matrix(ParArray[ParArray[Double]](
      ParArray[Double](2.0),
      ParArray[Double](-1.0),
      ParArray[Double](1.0)
    ))

    val resultMatrix = matrix1 * matrix2

    Assert.assertThat(resultMatrix.height, Is.is(1))
    Assert.assertThat(resultMatrix.width, Is.is(1))

    Assert.assertThat(resultMatrix.get(0, 0), Is.is(5.0))
  }

  @Test
  def testMultiplication2() {
    val matrix1 = new Matrix(ParArray[ParArray[Double]](
      ParArray[Double](2.0, 3.0),
      ParArray[Double](0.0, -2.0),
      ParArray[Double](-1.0, 4.0)
    ))

    val matrix2 = new Matrix(ParArray[ParArray[Double]](
      ParArray[Double](1.0, -1.0, 0.0, 3.0),
      ParArray[Double](2.0, 1.0, -2.0, -4.0)
    ))

    val resultMatrix = matrix1 * matrix2

    Assert.assertThat(resultMatrix.height, Is.is(3))
    Assert.assertThat(resultMatrix.width, Is.is(4))

    Assert.assertThat(resultMatrix.get(0, 0), Is.is(8.0))
    Assert.assertThat(resultMatrix.get(0, 1), Is.is(1.0))
    Assert.assertThat(resultMatrix.get(0, 2), Is.is(-6.0))
    Assert.assertThat(resultMatrix.get(0, 3), Is.is(-6.0))

    Assert.assertThat(resultMatrix.get(1, 0), Is.is(-4.0))
    Assert.assertThat(resultMatrix.get(1, 1), Is.is(-2.0))
    Assert.assertThat(resultMatrix.get(1, 2), Is.is(4.0))
    Assert.assertThat(resultMatrix.get(1, 3), Is.is(8.0))

    Assert.assertThat(resultMatrix.get(2, 0), Is.is(7.0))
    Assert.assertThat(resultMatrix.get(2, 1), Is.is(5.0))
    Assert.assertThat(resultMatrix.get(2, 2), Is.is(-8.0))
    Assert.assertThat(resultMatrix.get(2, 3), Is.is(-19.0))
  }
}
