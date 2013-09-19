package yaskoam.mrz2.lab1.ui.controllers

import javafx.fxml.FXML
import javafx.scene.image.{Image, ImageView}
import javafx.event.{EventHandler, Event, ActionEvent}
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import java.io.{File, FileInputStream}
import javafx.scene.layout.VBox
import javafx.scene.control.{Label, Tab, MenuItem}
import javafx.application.Platform
import javafx.scene.input.MouseEvent

/**
 * @author Q-YAA
 */
class MainSceneController {

  @FXML
  var rootPane: VBox = null

  @FXML
  var loadSourceImageMenuItem: MenuItem = null

  @FXML
  var sourceImage: ImageView = null


  @FXML
  var imageHeightLabel: Label = null

  @FXML
  var imageWidthLabel: Label = null

  def loadSourceImage(event: ActionEvent) {
    val file = createFileChooser().showOpenDialog(rootPane.getScene.getWindow)

    if (file != null) {
      val image = getImage(file)
      sourceImage.setImage(image)

      setSourceImageEventHandlers()
    }
  }

  def closeMainWindow(event: ActionEvent) {
    Platform.exit()
  }

  def disableLoadImageMenuItem(event: Event) {
    loadSourceImageMenuItem.setDisable(!event.getSource.asInstanceOf[Tab].isSelected)
  }

  def chooseImage(event: MouseEvent) {
    val imageView: ImageView = event.getSource.asInstanceOf[ImageView]
    imageView.scaleXProperty().setValue(1.1)
    imageView.scaleYProperty().setValue(1.1)

    imageHeightLabel.setText("image height: " + imageView.getImage.getHeight.toString)
    imageWidthLabel.setText("image width:" + imageView.getImage.getWidth.toString)
  }

  def unChooseImage(event: MouseEvent) {
    val imageView: ImageView = event.getSource.asInstanceOf[ImageView]
    imageView.scaleXProperty().setValue(1)
    imageView.scaleYProperty().setValue(1)

    imageHeightLabel.setText("")
    imageWidthLabel.setText("")
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

  private def setSourceImageEventHandlers() {

    sourceImage.setOnMouseEntered(new EventHandler[MouseEvent]() {
      def handle(event: MouseEvent) {
        chooseImage(event)
      }
    })

    sourceImage.setOnMouseExited(new EventHandler[MouseEvent]() {
      def handle(event: MouseEvent) {
        unChooseImage(event)
      }
    })
  }
}
