package yaskoam.mrz2.lab1.ui

import javafx.scene.image.{WritableImage, Image}
import yaskoam.mrz2.lab1.neuro.NeuroImage
import javafx.scene.paint.Color

/**
 * @author Q-YAA
 */
object Utils {

  def convertToNeuroImage(image: Image): NeuroImage = {

    val imageHeight = image.getHeight.toInt
    val imageWidth = image.getWidth.toInt

    val pixels = Array.ofDim[(Double, Double, Double)](imageHeight, imageWidth)

    for (i <- (0 until imageHeight).par; j <- (0 until imageWidth).par) {
      val color = image.getPixelReader.getColor(j, i)
      pixels(i)(j) = encodePixel(color)
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

  private def encodePixel(pix: Color) = (pix.getRed, pix.getGreen, pix.getBlue)

  private def decodePixel(pix: (Double, Double, Double)) = new Color(
    if (pix._1 > 1) 1 else if(pix._1 < 0) 0 else pix._1,
    if (pix._2 > 1) 1 else if(pix._2 < 0) 0 else pix._2,
    if (pix._3 > 1) 1 else if(pix._3 < 0) 0 else pix._3,
    1
  )
}
