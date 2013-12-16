package yaskoam.mrz2.lab3.ui.panels;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import yaskoam.mrz2.lab3.image.Image;
import yaskoam.mrz2.lab3.ui.BaseComponent;

/**
 * @author Q-YAA
 */
public class ImagePanel extends BaseComponent {

    @FXML
    private Canvas canvas;

    private Image image;

    public void setImage(Image image) {
        this.image = image;
        if (image != null) {
            paint(image);
        }
    }

    public Image getImage() {
        return image;
    }

    public void clear() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void paint(Image image) {
        canvas.setHeight(getHeight());
        canvas.setWidth(getWidth());

        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int pixelSize = calculatePixelSize(image.getWidth(), image.getHeight());

        paintImage(image, context, pixelSize);

        context.strokeRect(0, 0, image.getWidth() * pixelSize, image.getHeight() * pixelSize);
    }

    private void paintImage(Image image, GraphicsContext context, int pixelSize) {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (image.getValue(i, j) == 1) {
                    context.fillRect(j * pixelSize, i * pixelSize, pixelSize, pixelSize);
                }
            }
        }
    }

    private int calculatePixelSize(int imageWidth, int imageHeight) {
        return (int) Math.min(getWidth() / imageWidth, getHeight() / imageHeight);
    }
}
