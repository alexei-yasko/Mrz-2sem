package yaskoam.mrz2.lab1.neuro

/**
 * @author Q-YAA
 */
class NeuroImage(val pixels: Array[Array[(Double, Double, Double)]]) {

  def height = pixels.length

  def width = pixels(0).length
}
