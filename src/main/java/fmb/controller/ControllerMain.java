package fmb.controller;

import fmb.model.Product;
import fmb.serviceData.ProductData;
import fmb.tools.ErrorTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {
    @FXML
    private TableView<Product> tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<Product, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("productBrand"));

        TableColumn<Product, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));

        TableColumn<Product, String[]> ingredientsColumn = new TableColumn<>("Ingredients");
        ingredientsColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));

        tableView.getColumns().addAll(idColumn, nameColumn, brandColumn, typeColumn,ingredientsColumn);

        // Populate TableView with data
        ArrayList<Product> instance = ProductData.getInstance().getCurrentList();
        ObservableList<Product> products = FXCollections.observableArrayList(
                instance
        );
        tableView.setItems(products);
    }


    @FXML
    private void refreshData() {
        ProductData.getInstance().refreshData();
        alert("Data Refreshed", "Data has been refreshed successfully!");
    }
    @FXML
    private void newRow() {
        ProductData.getInstance().addProduct(new Product(123,"Brand","Name",new ArrayList<String>(),"type" ));
    }

    @FXML
    private void editRow() {
        ProductData.getInstance().updateProduct(new Product(123,"DifferentBrand","DifferentName",new ArrayList<String>(),"differenttype" ));
    }

    @FXML
    private void deleteRow() {
        ProductData.getInstance().deleteProduct(1);
    }

    @FXML
    public void alert(String title, String message) {
        ErrorTool.showAlert(title, message);
    }

    @FXML
    private void setConnection() {
        try {
            //load popup view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/view/ConnectionPopUp.fxml"));
            Parent root = loader.load();
            //set respective controller
            ConnectionPopUpController controller = loader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            //Setup to close popup by using stage.close within alert. See ShowAlertAndClose
            controller.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void clearConnection() {}
}
