package fmb;

import fmb.serviceData.CategoryData;
import fmb.serviceData.ProductData;
import fmb.serviceData.TypeData;
import fmb.tools.ErrorTool;
import javafx.application.Application;
import javafx.stage.Stage;
import fmb.tools.UI;

public class DataEntry extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            ProductData.getInstance().refreshData();
            CategoryData.getInstance().refreshData();
            TypeData.getInstance().refreshData();
        } catch (Exception e) {
            ErrorTool.showAlert("DATABASE ERROR", "Properties file error: connection expected different url OR username OR password");
        }
        new UI().start(stage);
    }

}
