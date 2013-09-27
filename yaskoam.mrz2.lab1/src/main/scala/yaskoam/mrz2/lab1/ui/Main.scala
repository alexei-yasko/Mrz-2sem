package yaskoam.mrz2.lab1.ui

import scalafx.application.JFXApp
import java.net.URL
import javafx.scene.Parent
import javafx.fxml.FXMLLoader
import scalafx.scene.Scene

/**
 * @author Q-YAA
 */
object Main extends JFXApp {

  val mainSceneResource: URL = getClass.getResource("mainScene.fxml")

  val root: Parent = FXMLLoader.load(mainSceneResource)

  stage = new JFXApp.PrimaryStage {
    title = "Image compression"
//    width = 900
//    height = 600
    scene = new Scene(delegate = new javafx.scene.Scene(root))
  }
}
