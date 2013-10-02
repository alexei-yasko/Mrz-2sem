package yaskoam.mrz2.lab1.ui.controllers;

import javafx.beans.property.StringProperty;
import yaskoam.mrz2.lab1.Logger;

/**
 * @author Q-YAA
 */
public class UiLogger implements Logger {

    private StringProperty totalErrorProperty;

    private StringProperty meanErrorProperty;

    private StringProperty iterationsProperty;

    public UiLogger(StringProperty totalErrorProperty, StringProperty meanErrorProperty, StringProperty iterationsProperty) {
        this.totalErrorProperty = totalErrorProperty;
        this.meanErrorProperty = meanErrorProperty;
        this.iterationsProperty = iterationsProperty;
    }

    @Override
    public void log(double totalError, double meanError, int iterations) {

        synchronized(this) {
            totalErrorProperty.set(Double.toString(totalError));
            meanErrorProperty.set(Double.toString(meanError));
            iterationsProperty.set(Integer.toString(iterations));
        }
    }
}
