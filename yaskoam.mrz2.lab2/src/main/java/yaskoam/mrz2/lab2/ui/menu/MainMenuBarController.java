package yaskoam.mrz2.lab2.ui.menu;

import javafx.application.Platform;
import javafx.event.ActionEvent;

/**
 * @author Q-YAA
 */
public class MainMenuBarController {

    public void closeMainWindow(ActionEvent event) {
        Platform.exit();
    }
}
