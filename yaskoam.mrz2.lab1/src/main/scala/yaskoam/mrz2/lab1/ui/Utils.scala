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

    val imageHeight = image.getHeight.toInt
    val imageWidth = image.getWidth.toInt

    val pixels = new ParArray[ParArray[(Double, Double, Double)]](imageHeight)
    for (i <- (0 until imageHeight).par) {

      val pixelsRow = new ParArray[(Double, Double, Double)](imageWidth)
      for (j <- (0 until imageWidth).par) {
        val color = image.getPixelReader.getColor(j, i)
        pixelsRow(j) = encodePixel(color)
      }

      pixels(i) = pixelsRow
    }

    new NeuroImage(pixels)
  }

  def convertFromNeuroImage(neuroImage: NeuroImage): Image = {
    val image = new WritableImage(neuroImage.width, neuroImage.height)

    for (i <- (0 until neuroImage.height).par; j <- (0 until neuroImage.width).par) {
      image.getPixelWriter.setColor(j, i, decodePixel(neuroImage.pixels(i)(j)))
    }

    image
  }

  private def encodePixel(pix: Color) =
    (encodeColor(pix.getRed), encodeColor(pix.getGreen), encodeColor(pix.getBlue))

  private def encodeColor(color: Double) = (2 * color / 255) - 1

  private def decodePixel(pix: (Double, Double, Double)) =
    new Color(decodeColor(pix._1), decodeColor(pix._2), decodeColor(pix._3), 1)

  private def decodeColor(color: Double) = ((color + 1) * 255) / 2
}
