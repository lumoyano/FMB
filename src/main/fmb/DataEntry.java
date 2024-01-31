package main.fmb;

import javafx.application.Application;
import javafx.stage.Stage;
import main.fmb.tools.UI;

public class DataEntry extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new UI().start(stage);
    }

}
