package fmb.controller;

import fmb.serviceData.ProductData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class ControllerMain {
    @FXML
    private void refreshData(){
        ProductData.getInstance().refreshData();
        showAlert("Data Refreshed", "Data has been refreshed successfully!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
