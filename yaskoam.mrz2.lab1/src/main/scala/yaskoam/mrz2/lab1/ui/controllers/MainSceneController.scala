package yaskoam.mrz2.lab1.ui.controllers

import javafx.fxml.FXML
import javafx.scene.image.{Image, ImageView}
import javafx.event.ActionEvent
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import java.io.FileInputStream
import javafx.scene.layout.BorderPane

/**
 * @author Q-YAA
 */
class MainSceneController {

  @FXML
  var borderPane: BorderPane = null

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

    val file = fileChooser.showOpenDialog(borderPane.getScene.getWindow)

    val imageStream = new FileInputStream(file)
    val image = new Image(imageStream)
    imageStream.close()

    sourceImage.setImage(image)
  }
}
