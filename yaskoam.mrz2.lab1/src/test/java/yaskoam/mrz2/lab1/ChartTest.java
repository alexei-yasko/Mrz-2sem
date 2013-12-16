//package yaskoam.mrz2.lab1;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//
//import org.apache.commons.io.IOUtils;
//import org.junit.Test;
//
//import javafx.scene.image.Image;
//import yaskoam.mrz2.lab1.neuro.NeuralNetwork;
//import yaskoam.mrz2.lab1.neuro.NeuroImage;
//
///**
// * @author Q-YAA
// */
//public class ChartTest {
//
//    @Test
//    public void testGenerateDataForChart() {
//        dataForChart1();
//        dataForChart2();
//        dataForChart3();
//    }
//
//    private void dataForChart3() {
//        int n = 8, m = 8;
//        int p = 24;
//        double e = 2400;
//
//        double[] aValues = new double[]{
//            0.005, 0.0045, 0.004, 0.0035, 0.003, 0.0025, 0.002, 0.0015, 0.001, 0.00095, 0.0009, 0.00085, 0.0008, 0.00075,
//            0.0007, 0.00065, 0.0006, 0.00055, 0.0005, 0.0004, 0.0003, 0.0002, 0.0001, 0.00009, 0.00008, 0.00007, 0.00006,
//            0.00005, 0.00004, 0.00003, 0.00002, 0.00001
//        };
//
//        String imageFile = "yaskoam.mrz2.lab1/images/image2(204x204).jpg";
//        Image image = loadImage(imageFile);
//        NeuroImage neuroImage = NeuroImage.fromImage(image);
//
//        int N = n * m * 3;
//        int L = (neuroImage.getHeight() / n) * (neuroImage.getWidth() / m);
//
//        for (double a : aValues) {
//
//            OutputStream outputStream = createOutputFile(3, a);
//            DefaultLogger defaultLogger = new DefaultLogger(outputStream);
//
//            NeuralNetwork neuralNetwork = new NeuralNetwork(N, p, a, e, Integer.MAX_VALUE);
//            neuralNetwork.setLogger(defaultLogger);
//            neuralNetwork.learn(neuroImage.splitIntoSegments(n, m));
//
//            double compressionCoefficient = ((N + L) * p + 2) / ((N * L) * 1.0);
//            defaultLogger.logCompressionCoefficient(compressionCoefficient);
//
//            IOUtils.closeQuietly(outputStream);
//        }
//    }
//
//    private void dataForChart2() {
//        int n = 8, m = 8;
//        int p = 24;
//        double a = 0.0005;
//
//        double[] eValues = new double[]{
//            2125.2, 2127, 2130, 2135, 2140, 2150, 2175, 2200, 2225, 2250, 2300, 2325, 2375, 2425, 2475, 2525, 2575, 2625, 2725,
//            2825, 2925,  3025, 3125, 3225, 3325, 3425, 3525, 3625, 3725, 3825, 3925, 4025, 4125, 4225, 4325, 4425, 4525, 4625,
//            4725, 5000, 5500, 6000, 7000
//        };
//
//        String imageFile = "yaskoam.mrz2.lab1/images/image2(204x204).jpg";
//        Image image = loadImage(imageFile);
//        NeuroImage neuroImage = NeuroImage.fromImage(image);
//
//        int N = n * m * 3;
//        int L = (neuroImage.getHeight() / n) * (neuroImage.getWidth() / m);
//
//        for (double e : eValues) {
//
//            OutputStream outputStream = createOutputFile(2, e);
//            DefaultLogger defaultLogger = new DefaultLogger(outputStream);
//
//            NeuralNetwork neuralNetwork = new NeuralNetwork(N, p, a, e, Integer.MAX_VALUE);
//            neuralNetwork.setLogger(defaultLogger);
//            neuralNetwork.learn(neuroImage.splitIntoSegments(n, m));
//
//            double compressionCoefficient = ((N + L) * p + 2) / ((N * L) * 1.0);
//            defaultLogger.logCompressionCoefficient(compressionCoefficient);
//
//            IOUtils.closeQuietly(outputStream);
//        }
//    }
//
//    private void dataForChart1() {
//
//        int n = 8, m = 8;
//        double a = 0.0005;
//        double e = 2125.2;
//
//        int[] pValues = new int[]{
//            24, 26, 28, 30, 34, 36, 40, 42, 44, 46, 52, 58, 64,
//            70, 76, 82, 88, 94, 100, 105, 110, 115, 120, 125, 130,  135, 140, 145, 150, 155, 160, 170, 180, 192
//        };
//
//        String imageFile = "yaskoam.mrz2.lab1/images/image2(204x204).jpg";
//        Image image = loadImage(imageFile);
//        NeuroImage neuroImage = NeuroImage.fromImage(image);
//
//        int N = n * m * 3;
//        int L = (neuroImage.getHeight() / n) * (neuroImage.getWidth() / m);
//
//        for (int p : pValues) {
//
//            OutputStream outputStream = createOutputFile(1, p);
//            DefaultLogger defaultLogger = new DefaultLogger(outputStream);
//
//            NeuralNetwork neuralNetwork = new NeuralNetwork(N, p, a, e, Integer.MAX_VALUE);
//            neuralNetwork.setLogger(defaultLogger);
//            neuralNetwork.learn(neuroImage.splitIntoSegments(n, m));
//
//            double compressionCoefficient = ((N + L) * p + 2) / ((N * L) * 1.0);
//            defaultLogger.logCompressionCoefficient(compressionCoefficient);
//
//            IOUtils.closeQuietly(outputStream);
//        }
//    }
//
//    private FileOutputStream createOutputFile(int chartNumber, double testLabel) {
//
//        String fileName = "results/result_" + chartNumber + "/" + testLabel + ".txt";
//
//        try {
//            return new FileOutputStream(fileName);
//        }
//        catch (FileNotFoundException e) {
//            throw new IllegalStateException("can't create test result file", e);
//        }
//    }
//
//    private Image loadImage(String fileName) {
//        FileInputStream imageStream = null;
//        try {
//            imageStream = new FileInputStream(fileName);
//            return new Image(imageStream);
//        }
//        catch (FileNotFoundException e) {
//            throw new IllegalStateException("can't read image file", e);
//        }
//        finally {
//            IOUtils.closeQuietly(imageStream);
//        }
//    }
//}
