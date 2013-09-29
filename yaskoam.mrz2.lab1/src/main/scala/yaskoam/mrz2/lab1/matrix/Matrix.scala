package yaskoam.mrz2.lab1.matrix

import scala.collection.parallel.mutable.ParArray

/**
 * @author Q-YAA
 */
class Matrix(private val elements: ParArray[ParArray[Double]]) {

  require(elements.length > 0, "elements array must not be empty")

  def height = elements.length

  def width = elements(0).length

  def get(i: Int, j: Int) = elements(i)(j)

  def set(i: Int, j: Int, value: Double) { elements(i)(j) = value }

  def transpose = new Matrix(ParArray.tabulate(width, height)((i, j) => elements(j)(i)))

  def *(matrix: Matrix) = {
    require(width == matrix.height, "wrong matrices sizes")

    val matrixMultiplicationElements = ParArray.tabulate(height, matrix.width)((i, j) => {
      (for (k <- 0 until matrix.height) yield { get(i, k) * matrix.get(k, j) }).sum
    })

    new Matrix(matrixMultiplicationElements)
  }

  override def toString = (for (row <- elements) yield { row.mkString(" ") }).mkString("\n")
}
