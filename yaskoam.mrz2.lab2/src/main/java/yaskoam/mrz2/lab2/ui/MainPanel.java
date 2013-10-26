package yaskoam.mrz2.lab2.ui;

import java.net.URL;
import java.util.ResourceBundle;

import org.jblas.DoubleMatrix;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import yaskoam.mrz2.lab2.ui.menu.MenuPanel;
import yaskoam.mrz2.lab2.ui.panels.ImagesPanel;
import yaskoam.mrz2.lab2.ui.panels.SettingsAndResultsPanel;

/**
 * @author Q-YAA
 */
public class MainPanel extends BaseComponent {

    @FXML
    private TextArea weightMatrix1TextArea;

    @FXML
    private TextArea weightMatrix2TextArea;

    @FXML
    private LineChart totalErrorChart;

    @FXML
    private ImagesPanel imagesPanel;

    @FXML
    private MenuPanel menuPanel;

    @FXML
    private SettingsAndResultsPanel settingsAndResultsPanel;

    private Thread calculationThread;

    private UiLogger uiLogger;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        uiLogger = createUiLogger();
        menuPanel.setMainPanel(this);
        initTotalErrorChart();
    }

    public ImagesPanel getImagePanel() {
        return imagesPanel;
    }

    private UiLogger createUiLogger() {
        StringProperty totalErrorProperty = new SimpleStringProperty();
        StringProperty meanErrorProperty = new SimpleStringProperty();
        StringProperty numberOfIterationsProperty = new SimpleStringProperty();

//        totalErrorTextField.textProperty().bindBidirectional(totalErrorProperty);
//        meanErrorTextField.textProperty().bindBidirectional(meanErrorProperty);
//        numberOfIterationsTextField.textProperty().bindBidirectional(numberOfIterationsProperty);

        return new UiLogger(totalErrorProperty, meanErrorProperty, numberOfIterationsProperty);
    }

    private void initTotalErrorChart() {
        ObservableList<XYChart.Data<Number, Number>> chartPoints = FXCollections.observableArrayList();
        XYChart.Series<Number, Number> totalErrorSeries = new XYChart.Series<Number, Number>(chartPoints);
        totalErrorChart.getData().add(totalErrorSeries);

        uiLogger.setChartPoints(chartPoints);

        totalErrorChart.getXAxis().setLabel("Iterations");
        totalErrorChart.getYAxis().setLabel("Total error");
    }

    private void clearTotalErrorChart() {
        XYChart.Series totalErrorSeries = (XYChart.Series) totalErrorChart.getData().get(0);
        totalErrorSeries.getData().clear();
    }

    private void disableResultTextFields() {
//        totalErrorTextField.setDisable(true);
//        meanErrorTextField.setDisable(true);
//        numberOfIterationsTextField.setDisable(true);
    }

    private void enableResultTextFields() {
//        totalErrorTextField.setDisable(false);
//        meanErrorTextField.setDisable(false);
//        numberOfIterationsTextField.setDisable(false);
    }

    private void displayWeightMatrices(DoubleMatrix weightMatrix1, DoubleMatrix weightMatrix2) {
        weightMatrix1TextArea.setText(formatWeightMatrixString(weightMatrix1));
        weightMatrix2TextArea.setText(formatWeightMatrixString(weightMatrix2));
    }

    private String formatWeightMatrixString(DoubleMatrix weightMatrix) {

        StringBuilder weightMatrixString = new StringBuilder(weightMatrix.toString("%.5f", "", "", " | ", "\n"));

        for (int i = 1; i < weightMatrixString.length() - 2; i++) {

            if (weightMatrixString.charAt(i) != '-'
                && weightMatrixString.charAt(i + 1) == '0' && weightMatrixString.charAt(i + 2) == '.') {

                weightMatrixString.insert(i + 1, " ");
                i++;
            }
        }

        return weightMatrixString.charAt(0) != '-' ?
            weightMatrixString.insert(0, " ").toString() : weightMatrixString.toString();
    }
}
