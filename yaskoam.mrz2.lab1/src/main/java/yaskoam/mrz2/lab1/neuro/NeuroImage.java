package yaskoam.mrz2.lab1.neuro;

import java.util.Arrays;

/**
 * @author Q-YAA
 */
public class NeuroImage {

    private double[][] pixels;

    private int height;

    private int width;

    public NeuroImage(double[][] pixels) {
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

    public double[][] splitIntoSegments(int n, int m) {

        int segmentsColumnCount = width / m;
        int segmentsRowCount = height / n;

        double[][] segmentedNeuroImage = new double[segmentsRowCount * segmentsColumnCount][n * m * 3];

        for (int i = 0; i <= height - n; i += n) {
            for (int j = 0; j < width - m; j += m) {

//                for (double[] row : Arrays.copyOfRange(pixels, i, i + n)) {
//                    double[] segment = Arrays.copyOfRange(row, j, j + m);
//
//                    double[] segment =
//
//                    for () {
//                    }
//                }
            }
        }

//      for (i <- (0 to(height - n, n)).par; j <- (0 to(width - m, m)).par) {
//
//        segmentedNeuroImage((i / n) * segmentsColumnCount + (j / m)) = (for (row <- pixels.slice(i, i + n)) yield {
//
//          row.slice(j, j + m).map(pix => Array(pix._1, pix._2, pix._3)).flatten
//
//        }).flatten
//      }
//
//      segmentedNeuroImage
    }
//
//    def collectFromSegments(n: Int, m: Int, segmentedNeuroImage: Array[Array[Double]]) {
//
//      val segmentsColumnCount = width / m
//
//      for (i <- (0 until(height - n, n)).par; j <- (0 until(width - m, m)).par) {
//
//        val segmentRowNumber = i / n
//        val segmentColumnNumber = j / m
//
//        val segmentNumber = segmentRowNumber * segmentsColumnCount + segmentColumnNumber
//
//        val segment = segmentedNeuroImage(segmentNumber)
//
//        for (k <- 0 until n; l <- 0 until(m * 3, 3)) {
//          val pixelPosition = k * m * 3 + l
//          pixels(i + k)(j + l / 3) = (segment(pixelPosition), segment(pixelPosition + 1), segment(pixelPosition + 2))
//        }
//      }
//    }
}
