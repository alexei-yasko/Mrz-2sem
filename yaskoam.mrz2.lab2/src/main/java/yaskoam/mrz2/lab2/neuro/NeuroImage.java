package yaskoam.mrz2.lab2.neuro;

import java.util.Arrays;

import com.google.common.primitives.Doubles;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * @author Q-YAA
 */
public class NeuroImage {

    private NeuroPixel[][] pixels;

    private int height;

    private int width;

    public NeuroImage(NeuroPixel[][] pixels) {
        this.pixels = pixels;
        this.height = pixels.length;
        this.width = pixels[0].length;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public NeuroPixel get(int i, int j) {
        return pixels[i][j];
    }

    public double[][] splitIntoSegments(int segmentHeight, int segmentWidth) {

        int segmentsColumnCount = width / segmentWidth;
        int segmentsRowCount = height / segmentHeight;

        double[][] segmentedNeuroImage = new double[segmentsRowCount * segmentsColumnCount][segmentHeight * segmentWidth * 3];

        for (int i = 0; i <= height - segmentHeight; i += segmentHeight) {
            for (int j = 0; j <= width - segmentWidth; j += segmentWidth) {

                double[][] segment = new double[segmentHeight][segmentWidth * 3];

                int index = 0;
                for (NeuroPixel[] segmentRow : Arrays.copyOfRange(pixels, i, i + segmentHeight)) {
                    segment[index++] = NeuroPixel.convertToSimple(Arrays.copyOfRange(segmentRow, j, j + segmentWidth));
                }

                segmentedNeuroImage[(i / segmentHeight) * segmentsColumnCount + (j / segmentWidth)] = Doubles.concat(segment);
            }
        }

        return segmentedNeuroImage;
    }

    public void collectFromSegments(int segmentHeight, int segmentWidth, double[][] segmentedNeuroImage) {

        int segmentsColumnCount = width / segmentWidth;

        for (int i = 0; i < height - segmentHeight; i += segmentHeight) {
            for (int j = 0; j < width - segmentWidth; j += segmentWidth) {

                int segmentRowNumber = i / segmentHeight;
                int segmentColumnNumber = j / segmentWidth;

                int segmentNumber = segmentRowNumber * segmentsColumnCount + segmentColumnNumber;

                double[] segment = segmentedNeuroImage[segmentNumber];

                for (int k = 0; k < segmentHeight; k++) {
                    for (int l = 0; l < segmentWidth * 3; l += 3) {
                        int pixelPosition = k * segmentWidth * 3 + l;
                        pixels[i + k][j + l / 3] = new NeuroPixel(
                            segment[pixelPosition], segment[pixelPosition + 1], segment[pixelPosition + 2]);
                    }
                }
            }
        }
    }

    public static NeuroImage fromImage(Image image) {

        int imageHeight = (int) image.getHeight();
        int imageWidth = (int) image.getWidth();

        NeuroPixel[][] pixels = new NeuroPixel[imageHeight][imageWidth];

        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                Color color = image.getPixelReader().getColor(j, i);
                pixels[i][j] = encodePixel(color);
            }
        }

        return new NeuroImage(pixels);
    }

    public static Image toImage(NeuroImage neuroImage) {
        WritableImage image = new WritableImage(neuroImage.getWidth(), neuroImage.getHeight());

        for (int i = 0; i < neuroImage.getHeight(); i++) {
            for (int j = 0; j < neuroImage.getWidth(); j++) {
                image.getPixelWriter().setColor(j, i, decodePixel(neuroImage.get(i, j)));
            }
        }

        return image;
    }

    private static Color decodePixel(NeuroPixel pixel) {
        double r = decodeColor(pixel.getR());
        double g = decodeColor(pixel.getG());
        double b = decodeColor(pixel.getB());

        return new Color(r > 1 ? 1 : r < 0 ? 0 : r, g > 1 ? 1 : g < 0 ? 0 : g, b > 1 ? 1 : b < 0 ? 0 : b, 1);
    }

    private static double decodeColor(double color) {
        return (color + 1) / 2;
    }

    private static NeuroPixel encodePixel(Color pixel) {
        return new NeuroPixel(encodeColor(pixel.getRed()), encodeColor(pixel.getGreen()), encodeColor(pixel.getBlue()));
    }

    private static double encodeColor(double color) {
        return (2 * color - 1);
    }
}
