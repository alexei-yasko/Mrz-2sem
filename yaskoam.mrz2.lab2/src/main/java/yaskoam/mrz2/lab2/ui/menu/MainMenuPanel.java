package yaskoam.mrz2.lab2.ui.menu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import yaskoam.mrz2.lab2.ui.BaseComponent;

/**
 * @author Q-YAA
 */
public class MainMenuPanel extends BaseComponent {

    @FXML
    private MenuBar mainMenuBar;

    public MainMenuPanel() {
    }

    public void closeMainWindow(ActionEvent event) {
        Platform.exit();
    }
}
