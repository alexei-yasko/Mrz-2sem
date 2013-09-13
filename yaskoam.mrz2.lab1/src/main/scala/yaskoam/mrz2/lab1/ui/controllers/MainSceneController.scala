package yaskoam.mrz2.lab1.ui.controllers

import javafx.fxml.FXML
import javafx.scene.image.{Image, ImageView}
import javafx.event.{Event, ActionEvent}
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import java.io.FileInputStream
import javafx.scene.layout.VBox
import javafx.scene.control.{Tab, MenuItem}
import javafx.application.Platform

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

  def loadSourceImage(event: ActionEvent) {

    val fileChooser = new FileChooser()
    fileChooser.setTitle("Choose Image")
    fileChooser.getExtensionFilters.addAll(
      new ExtensionFilter("JPEG", "*.jpg"),
      new ExtensionFilter("BMP", "*.bmp"),
      new ExtensionFilter("PNG", "*.png")
    )

    val file = fileChooser.showOpenDialog(rootPane.getScene.getWindow)

    if (file != null) {
      val imageStream = new FileInputStream(file)
      val image = new Image(imageStream)
      sourceImage.setImage(image)
      imageStream.close()
    }
  }

  def closeMainWindow(event: ActionEvent) {
    Platform.exit()
  }

  def disableLoadImageMenuItem(event: Event) {
    loadSourceImageMenuItem.setDisable(!event.getSource.asInstanceOf[Tab].isSelected)
  }
}
