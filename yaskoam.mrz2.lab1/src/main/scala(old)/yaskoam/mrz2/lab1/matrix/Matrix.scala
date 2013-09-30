package yaskoam.mrz2.lab1.matrix

/**
 * @author Q-YAA
 */
class Matrix(private val elements: Array[Array[Double]]) {

  def height = elements.length

  def width = elements(0).length

  def get(i: Int, j: Int) = elements(i)(j)

  def set(i: Int, j: Int, value: Double) { elements(i)(j) = value }

  def getRow(i: Int) = elements(i)

  def transpose = new Matrix(Array.tabulate(width, height)((i, j) => elements(j)(i)))

  def *(matrix: Matrix) = new Matrix(Array.tabulate(height, matrix.width)((i, j) => {
    (for(k <- 0 until width) yield get(i, k) * matrix.get(k, j)).sum
  }))

  def *(value: Double) = new Matrix(Array.tabulate(height, width)((i, j) => get(i, j) * value))

  def -(matrix: Matrix) = new Matrix(Array.tabulate(height, width)((i, j) => get(i, j) - matrix.get(i, j)))

  def +(matrix: Matrix) = new Matrix(Array.tabulate(height, width)((i, j) => get(i, j) + matrix.get(i, j)))

  override def toString = (for (row <- elements) yield { row.mkString(" ") }).mkString("\n")
}
