package yaskoam.mrz2.lab1.neuro

import scala.collection.parallel.mutable.ParArray

/**
 * @author Q-YAA
 */
class NeuroImage(val pixels: ParArray[ParArray[(Double, Double, Double)]]) {

  def height = pixels.length

  def width = pixels(0).length

  def splitAndEncode(n: Int, m: Int): ParArray[ParArray[Double]] = {
    null
  }
}
