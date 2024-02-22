package fmb.tools;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ErrorTool {
    @FXML
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public static void showAlertAndClose(String title, String message, Stage stage) {
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
}
