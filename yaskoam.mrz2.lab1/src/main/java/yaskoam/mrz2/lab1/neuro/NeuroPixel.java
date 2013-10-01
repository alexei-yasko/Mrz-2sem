package yaskoam.mrz2.lab1.neuro;

/**
 * @author Q-YAA
 */
public class NeuroPixel {

    private float r;

    private float g;

    private float b;

    public NeuroPixel(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public static float[] convertToSimple(NeuroPixel[] array) {
        float[] simpleArray = new float[array.length * 3];

        int index = 0;
        for (NeuroPixel pixel : array) {
            simpleArray[index++] = pixel.getR();
            simpleArray[index++] = pixel.getG();
            simpleArray[index++] = pixel.getB();
        }

        return simpleArray;
    }
}
