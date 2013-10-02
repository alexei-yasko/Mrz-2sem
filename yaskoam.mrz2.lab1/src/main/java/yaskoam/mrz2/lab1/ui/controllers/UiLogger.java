package yaskoam.mrz2.lab1.ui.controllers;

import java.text.DecimalFormat;

import javafx.beans.property.StringProperty;
import yaskoam.mrz2.lab1.Logger;

/**
 * @author Q-YAA
 */
public class UiLogger implements Logger {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.################");

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
            totalErrorProperty.set(FORMAT.format(totalError * -1));
            meanErrorProperty.set(FORMAT.format(meanError));
            iterationsProperty.set(FORMAT.format(iterations));
        }
    }
}
