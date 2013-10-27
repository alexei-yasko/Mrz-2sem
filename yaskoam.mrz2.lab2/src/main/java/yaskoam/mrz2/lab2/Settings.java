package yaskoam.mrz2.lab2;

/**
 * @author Q-YAA
 */
public class Settings {

    private static Settings settings = new Settings();

    private int segmentHeight;
    private int segmentWidth;
    private int secondLayerNeurons;
    private double learningCoefficient;
    private double maxError;
    private int maxIterations;

    private Settings() {
    }

    synchronized public static Settings get() {
        return settings;
    }

    public static Settings getSettings() {
        return settings;
    }

    public int getSegmentHeight() {
        return segmentHeight;
    }

    public int getSegmentWidth() {
        return segmentWidth;
    }

    public int getSecondLayerNeurons() {
        return secondLayerNeurons;
    }

    public double getLearningCoefficient() {
        return learningCoefficient;
    }

    public double getMaxError() {
        return maxError;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public static void setSettings(Settings settings) {
        Settings.settings = settings;
    }

    public void setSegmentHeight(int segmentHeight) {
        this.segmentHeight = segmentHeight;
    }

    public void setSegmentWidth(int segmentWidth) {
        this.segmentWidth = segmentWidth;
    }

    public void setSecondLayerNeurons(int secondLayerNeurons) {
        this.secondLayerNeurons = secondLayerNeurons;
    }

    public void setLearningCoefficient(double learningCoefficient) {
        this.learningCoefficient = learningCoefficient;
    }

    public void setMaxError(double maxError) {
        this.maxError = maxError;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }
}
