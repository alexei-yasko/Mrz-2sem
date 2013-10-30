package yaskoam.mrz2.lab2.ui;

import java.text.DecimalFormat;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import yaskoam.mrz2.lab2.Logger;

/**
 * @author Q-YAA
 */
public class UiLogger implements Logger {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.################");

    private StringProperty totalErrorProperty;

    private StringProperty meanErrorProperty;

    private StringProperty iterationsProperty;

    private ObservableList<XYChart.Data<Number, Number>> chartPoints;

    public UiLogger(StringProperty totalErrorProperty, StringProperty meanErrorProperty, StringProperty iterationsProperty) {
        this.totalErrorProperty = totalErrorProperty;
        this.meanErrorProperty = meanErrorProperty;
        this.iterationsProperty = iterationsProperty;
    }

    @Override
    public void log(final double totalError, final int iterations) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                totalErrorProperty.set(FORMAT.format(totalError));
                iterationsProperty.set(FORMAT.format(iterations));

                if (chartPoints != null) {
                    chartPoints.add(new XYChart.Data<Number, Number>(iterations, totalError));
                }
            }
        });
    }

    public void setChartPoints(ObservableList<XYChart.Data<Number, Number>> chartPoints) {
        this.chartPoints = chartPoints;
    }
}
