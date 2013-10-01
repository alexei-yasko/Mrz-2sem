package yaskoam.mrz2.lab1.neuro;

/**
 * @author Q-YAA
 */
public class NeuroPixel {

    private double r;

    private double g;

    private double b;

    public NeuroPixel(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double getR() {
        return r;
    }

    public double getG() {
        return g;
    }

    public double getB() {
        return b;
    }

    public static double[] convertToSimple(NeuroPixel[] array) {
        double[] simpleArray = new double[array.length * 3];

        int index = 0;
        for (NeuroPixel pixel : array) {
            simpleArray[index++] = pixel.getR();
            simpleArray[index++] = pixel.getG();
            simpleArray[index++] = pixel.getB();
        }

        return simpleArray;
    }
}
