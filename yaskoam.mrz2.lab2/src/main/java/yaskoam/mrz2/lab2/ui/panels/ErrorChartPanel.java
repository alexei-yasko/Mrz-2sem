package yaskoam.mrz2.lab2.ui.panels;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import yaskoam.mrz2.lab2.ui.BaseComponent;

/**
 * @author Q-YAA
 */
public class ErrorChartPanel extends BaseComponent {

    @FXML
    private LineChart errorChart;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        initTotalErrorChart();
    }

    public void clearErrorChart() {
        XYChart.Series totalErrorSeries = (XYChart.Series) errorChart.getData().get(0);
        totalErrorSeries.getData().clear();
    }

    public ObservableList<XYChart.Data<Number, Number>> getChartPoints() {
        return ((XYChart.Series) errorChart.getData().get(0)).getData();
    }

    private void initTotalErrorChart() {
        ObservableList<XYChart.Data<Number, Number>> chartPoints = FXCollections.observableArrayList();
        XYChart.Series<Number, Number> totalErrorSeries = new XYChart.Series<Number, Number>(chartPoints);
        errorChart.getData().add(totalErrorSeries);

        errorChart.getXAxis().setLabel("Iterations");
        errorChart.getYAxis().setLabel("Total error");
    }
}
