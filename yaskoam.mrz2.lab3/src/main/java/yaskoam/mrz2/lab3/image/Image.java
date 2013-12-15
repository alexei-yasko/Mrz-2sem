package yaskoam.mrz2.lab3.image;

/**
 * @author Q-YAA
 */
public class Image {

    private int width;

    private int height;

    private int[][] values;

    public Image(int[][] values, int width, int height) {
        this.width = width;
        this.height = height;
        this.values = values;
    }

    public int[] flattenValues() {
        int[] vector = new int[width * height];
        for (int i = 0; i < height; i++) {
            System.arraycopy(values[i], 0, vector, i * width, width);
        }
        return vector;
    }

    public static Image fromFlatten(int[] values, int width, int height) {
        int[][] valuesArray = new int[height][width];
        for (int i = 0; i < height; i++) {
            System.arraycopy(values, i * width, valuesArray[i], 0, width);
        }

        return new Image(valuesArray, width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getValue(int row, int column) {
        return values[row][column];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }

        Image image = (Image) o;

        if (height != image.height) {
            return false;
        }
        if (width != image.width) {
            return false;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (values[i][j] != image.getValue(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }
}
