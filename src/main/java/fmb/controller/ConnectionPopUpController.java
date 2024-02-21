package fmb.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.awt.event.ActionEvent;

public class ConnectionPopUpController {
    @FXML
    public void save () {

    }

    @FXML
    private void showAlertAndClose(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
