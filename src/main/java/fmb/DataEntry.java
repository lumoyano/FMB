package fmb;

import fmb.ServiceData.ProductData;
import fmb.tools.DBConfig;
import javafx.application.Application;
import javafx.stage.Stage;
import fmb.tools.UI;

import fmb.tools.ScraperTool;

import java.io.IOException;

public class DataEntry extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ProductData.getInstance().refreshData();
        new UI().start(stage);
    }

}
