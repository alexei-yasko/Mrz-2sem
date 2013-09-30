package yaskoam.mrz2.lab1.neuro

import yaskoam.mrz2.lab1.matrix.{MatrixJava, Matrix}
import java.util.Calendar

/**
 * @author Q-YAA
 */
class NeuralNetwork(val n: Int, val p: Int, val maxError: Double, val a: Double) {

  var W1 = new MatrixJava(Array.fill(n, p)(math.random))

  var W2 = new MatrixJava(Array.fill(p, n)(math.random))

  def compress(segments: Array[Array[Double]]): Array[Array[Double]] = {
    require(segments.length > 0 && segments(0).length == n, "wrong segment size")

    val compressed = new Array[Array[Double]](segments.length)

    for (i <- 0 until segments.length) {
      val X = new MatrixJava(Array(segments(i)))
      compressed(i) = (X * W1).getRow(0)
    }

    compressed
  }

  def decompress(segments: Array[Array[Double]]): Array[Array[Double]] = {
    require(segments.length > 0 && segments(0).length == p, "wrong segment size")

    val decompressed = new Array[Array[Double]](segments.length)

    for (i <- 0 until segments.length) {
      val Y = new MatrixJava(Array(segments(i)))
      decompressed(i) = (Y * W2).getRow(0)
    }

    decompressed
  }

  def learn(segments: Array[Array[Double]]) {

    var error = 0.0

    do {

      val currentTime = Calendar.getInstance().getTimeInMillis

      // learn
      for (i <- 0 until segments.length) {

        val X = new MatrixJava(Array(segments(i)))
        val Y = X * W1
        val deltaX = Y * W2 - X

        W1 -= X.transpose * deltaX * W2.transpose * a
        W2 -= Y.transpose * deltaX * a
      }

      error = 0.0

      // calculate error
      for (i <- 0 until segments.length) {
        val X = new MatrixJava(Array(segments(i)))
        val Y = X * W1
        val deltaX = Y * W2 - X

        error += (for (j <- 0 until deltaX.getWidth) yield { deltaX.get(0, j) * deltaX.get(0, j) }).sum
      }

      println(error.toString + ", Time: " + (Calendar.getInstance().getTimeInMillis - currentTime))

    } while (error > maxError)
  }
}
