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
}
