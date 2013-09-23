package yaskoam.mrz2.lab1.neuro

import scala.collection.parallel.mutable.ParArray
import scala.collection.mutable

/**
 * @author Q-YAA
 */
class NeuroImage(val pixels: ParArray[ParArray[(Double, Double, Double)]]) {

  def height = pixels.length

  def width = pixels(0).length

  def split(n: Int, m: Int): ParArray[ParArray[Double]] = {

    var splittedNeuroImage = new mutable.MutableList[ParArray[Double]]()

    for (i <- 0 until(height - n, n)) {
      for (j <- 0 until(width - m, m)) {

        splittedNeuroImage += (for (row <- pixels.slice(i, i + n)) yield {

          row.slice(j, m).map(pix => Array(pix._1, pix._2, pix._3)).flatten

        }).flatten

      }
    }

    splittedNeuroImage.toArray.par
  }

  def collect(n: Int, m: Int, splittedNeuroImage: ParArray[ParArray[Double]]) {

  }
}
