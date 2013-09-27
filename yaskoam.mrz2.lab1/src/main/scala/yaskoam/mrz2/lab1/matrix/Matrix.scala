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

//    val result = ParArray.tabulate(height, matrix.width)((i, j) => {
//      for ()
//    })
//
//    val result =  ParArray.fill(height, matrix.width)(0.0)
//    val result = new ParArray[]()
//
//    for(row <- (0 until m1.length).par;
//        col <- (0 until m2(0).length).par;
//        i   <- 0 until m1(0).length){
//
//      result(row)(col) += m1(row)(i) * m2(i)(col)
//    }
//    result
  }

  override def toString = (for (row <- elements) yield { row.mkString(" ") }).mkString("\n")
}
