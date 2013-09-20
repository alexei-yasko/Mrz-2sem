package yaskoam.mrz2.lab1.ui

import javafx.scene.image.{WritableImage, Image}
import yaskoam.mrz2.lab1.neuro.NeuroImage
import javafx.scene.paint.Color
import scala.collection.parallel.mutable.ParArray

/**
 * @author Q-YAA
 */
object Utils {

  def convertToNeuroImage(image: Image): NeuroImage = {

    val pixels = ParArray.fill(image.getHeight.toInt, image.getWidth.toInt)(0.0, 0.0, 0.0)

    for (i <- 0 until pixels.length) {
      for (j <- 0 until pixels(i).length) {
        val pixel: Color = image.getPixelReader.getColor(j, i)
        pixels(i)(j) = encodePixel(pixel)
      }
    }

    new NeuroImage(pixels)
  }

  def convertFromNeuroImage(neuroImage: NeuroImage): Image = {
    val image = new WritableImage(neuroImage.width, neuroImage.height)

    for (i <- 0 until neuroImage.height) {
      for (j <- 0 until neuroImage.width) {
        image.getPixelWriter.setColor(j, i, decodePixel(neuroImage.pixels(i)(j)))
      }
    }

    image
  }

  private def encodePixel(pix: Color) =
    (encodeColor(pix.getRed), encodeColor(pix.getGreen), encodeColor(pix.getBlue))

  private def encodeColor(color: Double) = (2 * color / 255) - 1

  private def decodePixel(pix: (Double, Double, Double)) =
    new Color(decodeColor(pix._1), decodeColor(pix._2),decodeColor(pix._3), 1)

  private def decodeColor(color: Double) = ((color + 1) * 255) / 2
}
