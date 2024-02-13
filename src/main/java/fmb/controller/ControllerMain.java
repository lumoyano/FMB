package fmb.controller;

import fmb.model.Product;
import fmb.serviceData.ProductData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private void refreshData(){
        ProductData.getInstance().refreshData();
        showAlert("Data Refreshed", "Data has been refreshed successfully!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
