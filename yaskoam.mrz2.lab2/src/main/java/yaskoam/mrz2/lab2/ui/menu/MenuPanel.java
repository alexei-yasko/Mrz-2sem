package yaskoam.mrz2.lab2.ui.menu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import yaskoam.mrz2.lab2.ui.BaseComponent;

/**
 * @author Q-YAA
 */
public class MenuPanel extends BaseComponent {

    public void closeMainWindow(ActionEvent event) {
        Platform.exit();
    }
}
