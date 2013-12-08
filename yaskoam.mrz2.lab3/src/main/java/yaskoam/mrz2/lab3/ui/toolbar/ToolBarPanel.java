package yaskoam.mrz2.lab3.ui.toolbar;

import java.io.File;

import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import yaskoam.mrz2.lab3.image.Image;
import yaskoam.mrz2.lab3.ui.BaseComponent;
import yaskoam.mrz2.lab3.ui.MainPanel;
import yaskoam.mrz2.lab3.ui.panels.EditImagePanel;

/**
 * @author Q-YAA
 */
public class ToolBarPanel extends BaseComponent {

    private MainPanel mainPanel;

    public void loadImage() {
        File file = createFileChooser().showOpenDialog(mainPanel.getScene().getWindow());
        if (file != null) {
            mainPanel.getSourceImagePanel().setImage(Image.fromFile(file));
        }
    }

    public void editImage() {
        Stage stage = new Stage();
        EditImagePanel editImagePanel = new EditImagePanel(mainPanel.getSourceImagePanel().getImage());
        stage.setScene(new Scene(editImagePanel));
        stage.setTitle("Edit image");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(getScene().getWindow());
        stage.showAndWait();

        if (editImagePanel.isImageEditted()) {
            mainPanel.getSourceImagePanel().setImage(editImagePanel.getImage());
        }
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    private FileChooser createFileChooser() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose Image");
        fileChooser.setInitialDirectory(new File("yaskoam.mrz2.lab3/images/"));

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image files", "*.img")
        );

        return fileChooser;
    }
}
