package fmb.controller;

import fmb.tools.DBConfig;
import fmb.tools.ErrorTool;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        if (urlField.getText().isEmpty() ||
        usernameField.getText().isEmpty() ||
        passwdField.getText().isEmpty())
            ErrorTool.showAlert("Error", "One or more fields are empty. Please make sure all fields are filled and try again.");
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
        ErrorTool.showAlertAndClose("Connection",
                "Information saved. Please refresh data before modifying the tables.",
                this.stage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
