package fmb.controller;

import fmb.tools.DBConfig;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class ConnectionPopUpController {
    @FXML
    private Stage stage;

    @FXML
    private TextField urlField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwdField;


    @FXML
    public void save () {
        try {
            DBConfig.setProperties(
                    urlField.getText(),
                    usernameField.getText(),
                    passwdField.getText()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        //notify success and close
        showAlertAndClose("Connection", "Information saved. Please refresh data before modifying the tables.");
    }

    @FXML
    public void clearConnection() {
        
    }

    @FXML
    private void showAlertAndClose(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.setOnCloseRequest(dialogEvent -> {
            alert.close();
            stage.close();
        });

        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
