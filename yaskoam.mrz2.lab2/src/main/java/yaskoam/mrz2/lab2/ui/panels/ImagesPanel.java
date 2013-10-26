package yaskoam.mrz2.lab2.ui.panels;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import yaskoam.mrz2.lab2.ui.BaseComponent;

/**
 * @author Q-YAA
 */
public class ImagesPanel extends BaseComponent {

    @FXML
    private ImageView sourceImageView;

    @FXML
    private ImageView resultImageView;

    @FXML
    private Label imageHeightLabel;

    @FXML
    private Label imageWidthLabel;

    public ImagesPanel() {
    }

    public void setSourceImage(Image image) {
        sourceImageView.setImage(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        setImageViewsEventHandlers();

    }

    private void setImageViewsEventHandlers() {

        sourceImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                chooseImage(event);
            }
        });

        sourceImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                unChooseImage(event);
            }
        });

        resultImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                chooseImage(event);
            }
        });

        resultImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                unChooseImage(event);
            }
        });
    }

    private void chooseImage(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();

        if (imageView.getImage() != null) {
            imageView.scaleXProperty().setValue(1.1);
            imageView.scaleYProperty().setValue(1.1);

            imageHeightLabel.setText("image height: " + imageView.getImage().getHeight());
            imageWidthLabel.setText("image width:" + imageView.getImage().getWidth());
        }
    }

    private void unChooseImage(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();

        if (imageView.getImage() != null) {
            imageView.scaleXProperty().setValue(1);
            imageView.scaleYProperty().setValue(1);

            imageHeightLabel.setText("");
            imageWidthLabel.setText("");
        }
    }
}
