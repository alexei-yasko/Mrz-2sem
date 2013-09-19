package yaskoam.mrz2.lab1.ui

import javafx.scene.image.{WritableImage, PixelWriter, Image}
import yaskoam.mrz2.lab1.neuro.NeuroImage
import javafx.scene.paint.Color

/**
 * @author Q-YAA
 */
object Utils {

  def convertToNeuroImage(image: Image): NeuroImage = {

    val pixels: Array[Array[(Double, Double, Double)]] =
      Array.ofDim[(Double, Double, Double)](image.getHeight.toInt, image.getWidth.toInt)

    for (i <- 0 until pixels.length) {
      for (j <- 0 until pixels(i).length) {

        val color: Color = image.getPixelReader.getColor(j, i)
        pixels(i)(j) = (color.getRed, color.getGreen, color.getBlue)
      }
    }

    println(pixels.length)
    println(pixels(0).length)

    new NeuroImage(pixels)
  }

  def convertFromNeuroImage(neuroImage: NeuroImage): Image = {
    val image = new WritableImage(neuroImage.width, neuroImage.height)

    for (i <- 0 until neuroImage.height) {
      for (j <- 0 until neuroImage.width) {

        val pixel: (Double, Double, Double) = neuroImage.pixels(i)(j)
        image.getPixelWriter.setColor(j, i, new Color(pixel._1, pixel._2, pixel._3, 1.0))
      }
    }

    image
  }
}
