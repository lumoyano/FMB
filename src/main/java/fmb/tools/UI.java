package fmb.tools;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../../resources/view/ViewMain.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("FMB - DB Manager");
        stage.setScene(new Scene(root, 900, 700));
        stage.show();
    }
}
