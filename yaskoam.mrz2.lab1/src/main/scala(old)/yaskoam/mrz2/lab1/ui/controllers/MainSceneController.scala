package yaskoam.mrz2.lab1.ui.controllers

import javafx.fxml.FXML
import javafx.scene.image.{Image, ImageView}
import javafx.event.{EventHandler, Event, ActionEvent}
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import java.io.{File, FileInputStream}
import javafx.scene.layout.VBox
import javafx.scene.control.{TextField, Label, Tab, MenuItem}
import javafx.application.Platform
import javafx.scene.input.MouseEvent
import java.util.Calendar
import yaskoam.mrz2.lab1.ui.Utils
import yaskoam.mrz2.lab1.neuro.NeuralNetwork

/**
 * @author Q-YAA
 */
class MainSceneController {

  @FXML
  var rootPane: VBox = null

  @FXML
  var loadSourceImageMenuItem: MenuItem = null

  @FXML
  var sourceImageView: ImageView = null

  @FXML
  var resultImageView: ImageView = null

  @FXML
  var imageHeightLabel: Label = null

  @FXML
  var imageWidthLabel: Label = null

  @FXML
  var nTextField: TextField = null

  @FXML
  var mTextField: TextField = null

  @FXML
  var pTextField: TextField = null

  @FXML
  var aTextField: TextField = null

  @FXML
  var a1TextField: TextField = null

  @FXML
  var eTextField: TextField = null

  def loadSourceImage(event: ActionEvent) {
    val file = createFileChooser().showOpenDialog(rootPane.getScene.getWindow)

    if (file != null) {
      val image: Image = getImage(file)
      sourceImageView.setImage(image)

      setImageViewsEventHandlers()
    }
  }

  def compressSourceImage() {
    val currentTime = Calendar.getInstance().getTimeInMillis

    val image = sourceImageView.getImage

    val n = 3
    val m = 3
    val p = 4
    val a = 0.005
    val e = 0.01

    if (image != null) {

      new Thread(new Runnable {
        def run() {
          val neuroImage = Utils.convertToNeuroImage(image)
          val segments = neuroImage.splitIntoSegments(n, m)

          val neuralNetwork = new NeuralNetwork(n * m * 3, p, e, a)
          neuralNetwork.learn(segments)
          val compressedSegments = neuralNetwork.compress(segments)
          val decompressedSegments = neuralNetwork.decompress(compressedSegments)

          neuroImage.collectFromSegments(n, m, decompressedSegments)

          val resultImage = Utils.convertFromNeuroImage(neuroImage)
          resultImageView.setImage(resultImage)
        }
      }).start()
    }

    println("Time: " + (Calendar.getInstance().getTimeInMillis - currentTime).toString)
  }

  def closeMainWindow(event: ActionEvent) {
    Platform.exit()
  }

  def disableLoadImageMenuItem(event: Event) {
    loadSourceImageMenuItem.setDisable(!event.getSource.asInstanceOf[Tab].isSelected)
  }

  def chooseImage(event: MouseEvent) {
    val imageView: ImageView = event.getSource.asInstanceOf[ImageView]

    if (imageView.getImage != null) {
      imageView.scaleXProperty().setValue(1.1)
      imageView.scaleYProperty().setValue(1.1)

      imageHeightLabel.setText("image height: " + imageView.getImage.getHeight.toString)
      imageWidthLabel.setText("image width:" + imageView.getImage.getWidth.toString)
    }
  }

  def unChooseImage(event: MouseEvent) {
    val imageView: ImageView = event.getSource.asInstanceOf[ImageView]

    if (imageView.getImage != null) {
      imageView.scaleXProperty().setValue(1)
      imageView.scaleYProperty().setValue(1)

      imageHeightLabel.setText("")
      imageWidthLabel.setText("")
    }
  }

  private def getImage(file: File): Image = {
    val imageStream = new FileInputStream(file)
    val image = new Image(imageStream)
    imageStream.close()
    image
  }

  private def createFileChooser(): FileChooser = {

    val fileChooser = new FileChooser()

    fileChooser.setTitle("Choose Image")

    fileChooser.getExtensionFilters.addAll(
      new ExtensionFilter("JPEG", "*.jpg"),
      new ExtensionFilter("BMP", "*.bmp"),
      new ExtensionFilter("PNG", "*.png")
    )

    fileChooser
  }

  private def setImageViewsEventHandlers() {

    sourceImageView.setOnMouseEntered(new EventHandler[MouseEvent]() {
      def handle(event: MouseEvent) {
        chooseImage(event)
      }
    })

    sourceImageView.setOnMouseExited(new EventHandler[MouseEvent]() {
      def handle(event: MouseEvent) {
        unChooseImage(event)
      }
    })

    resultImageView.setOnMouseEntered(new EventHandler[MouseEvent]() {
      def handle(event: MouseEvent) {
        chooseImage(event)
      }
    })

    resultImageView.setOnMouseExited(new EventHandler[MouseEvent]() {
      def handle(event: MouseEvent) {
        unChooseImage(event)
      }
    })
  }
}
