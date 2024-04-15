package fmb;

import fmb.serviceData.CategoryData;
import fmb.serviceData.ProductData;
import fmb.serviceData.TypeData;
import javafx.application.Application;
import javafx.stage.Stage;
import fmb.tools.UI;

public class DataEntry extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ProductData.getInstance().refreshData();
        CategoryData.getInstance().refreshData();
        TypeData.getInstance().refreshData();
        new UI().start(stage);
    }

}
