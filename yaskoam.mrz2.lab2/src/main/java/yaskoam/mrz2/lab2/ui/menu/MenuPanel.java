package yaskoam.mrz2.lab2.ui.menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.io.IOUtils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import yaskoam.mrz2.lab2.ui.BaseComponent;
import yaskoam.mrz2.lab2.ui.MainPanel;

/**
 * @author Q-YAA
 */
public class MenuPanel extends BaseComponent {

    private MainPanel mainPanel;

    public MenuPanel() {
    }

    public void closeMainWindow(ActionEvent event) {
        Platform.exit();
    }

    public void loadSourceImage(ActionEvent event) {
        File file = createFileChooser().showOpenDialog(mainPanel.getScene().getWindow());

        if (file != null) {
            Image image = getImage(file);
            mainPanel.getImagePanel().setSourceImage(image);
        }
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    private FileChooser createFileChooser() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose Image");
        fileChooser.setInitialDirectory(new File("yaskoam.mrz2.lab1/images/"));

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JPEG", "*.jpg"),
            new FileChooser.ExtensionFilter("BMP", "*.bmp"),
            new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        return fileChooser;
    }

    private Image getImage(File file) {
        FileInputStream imageStream = null;
        try {
            imageStream = new FileInputStream(file);
            return new Image(imageStream);
        }
        catch (FileNotFoundException e) {
            throw new IllegalStateException("can't read image file", e);
        }
        finally {
            IOUtils.closeQuietly(imageStream);
        }
    }
}
