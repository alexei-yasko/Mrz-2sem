package yaskoam.mrz2.lab1.ui.controllers;

import java.text.DecimalFormat;

import javafx.application.Platform;
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
    public void log(final double totalError, final double meanError, final int iterations) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                totalErrorProperty.set(FORMAT.format(totalError));
                meanErrorProperty.set(FORMAT.format(meanError));
                iterationsProperty.set(FORMAT.format(iterations));
            }
        });
    }
}
