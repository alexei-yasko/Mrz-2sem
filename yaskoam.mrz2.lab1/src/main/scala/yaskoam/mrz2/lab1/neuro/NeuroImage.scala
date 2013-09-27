package yaskoam.mrz2.lab1.neuro

import scala.collection.parallel.mutable.ParArray

/**
 * @author Q-YAA
 */
class NeuroImage(val pixels: ParArray[ParArray[(Double, Double, Double)]]) {

  require(pixels.length > 0, "pixels array must not be empty")

  def height = pixels.length

  def width = pixels(0).length

  def splitIntoSegments(n: Int, m: Int): ParArray[ParArray[Double]] = {

    val segmentsColumnCount = width / m
    val segmentsRowCount = height / n

    val segmentedNeuroImage = new ParArray[ParArray[Double]](segmentsRowCount * segmentsColumnCount)

    for (i <- (0 to(height - n, n)).par) {
      for (j <- (0 to(width - m, m)).par) {

        segmentedNeuroImage((i / n) * segmentsColumnCount + (j / m)) = (for (row <- pixels.slice(i, i + n)) yield {

          row.slice(j, j + m).map(pix => ParArray(pix._1, pix._2, pix._3)).flatten

        }).flatten

      }
    }

    segmentedNeuroImage
  }

  def collectFromSegments(n: Int, m: Int, segmentedNeuroImage: ParArray[ParArray[Double]]) {

    val segmentsColumnCount = width / m

    for (i <- (0 until(height - n, n)).par) {
      for (j <- (0 until(width - m, m)).par) {

        val segmentRowNumber = i / n
        val segmentColumnNumber = j / m

        val segmentNumber = segmentRowNumber * segmentsColumnCount + segmentColumnNumber

        val segment = segmentedNeuroImage(segmentNumber)

        for (k <- 0 until n) {
          for (l <- 0 until(m * 3, 3)) {

            val pixelPosition = k * m * 3 + l
            pixels(i + k)(j + l / 3) = (segment(pixelPosition), segment(pixelPosition + 1), segment(pixelPosition + 2))
          }
        }
      }
    }
  }
}
