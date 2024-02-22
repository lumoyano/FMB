package fmb.controller;

import fmb.model.Product;
import fmb.serviceData.ProductData;
import fmb.tools.DBConfig;
import fmb.tools.ErrorTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    @FXML
    private ChoiceBox<String> searchChoiceBox;

    private String[] options = {"ID", "Name", "Brand", "Type"};

    @FXML
    private TextField searchTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchChoiceBox.getItems().addAll(options);
        searchChoiceBox.setValue("ID");
        populateTableValues();
    }

    @FXML
    private void populateTableValues() {
        // Clear TableView
        tableView.getItems().clear();
        tableView.getColumns().clear();

        TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        idColumn.setPrefWidth(25);
        idColumn.setId("ID");

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        idColumn.setId("Name");

        TableColumn<Product, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("productBrand"));
        idColumn.setId("Brand");

        TableColumn<Product, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
        idColumn.setId("Type");

        TableColumn<Product, String[]> ingredientsColumn = new TableColumn<>("Ingredients");
        ingredientsColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        idColumn.setId("Ingredients");

        tableView.getColumns().addAll(idColumn, nameColumn, brandColumn, typeColumn,ingredientsColumn);

        // Populate TableView with data
        ArrayList<Product> instance = ProductData.getInstance().getCurrentList();
        ObservableList<Product> products = FXCollections.observableArrayList(
                instance
        );
        tableView.setItems(products);
    }

//    @FXML
//    private void updateTableView() {
//        String searchQuery = searchTextField.getText().toLowerCase(); // Get search query from search bar input
//        String selectedOption = searchChoiceBox.getValue(); // Get selected search option from choice box
//
//        // Filter the data in your TableView based on the search query and selected search option
//        ObservableList<Product> filteredData = ProductData.getInstance().getCurrentList().filtered(product -> {
//            // Convert product details to lowercase for case-insensitive search
//            String fieldValue = "";
//            switch (selectedOption) {
//                case "ID":
//                    fieldValue = String.valueOf(product.getProductID());
//                    break;
//                case "Name":
//                    fieldValue = product.getProductName().toLowerCase();
//                    break;
//                case "Brand":
//                    fieldValue = product.getProductBrand().toLowerCase();
//                    break;
//                case "Type":
//                    fieldValue = product.getProductType().toLowerCase();
//                    break;
//            }
//            return fieldValue.contains(searchQuery);
//        });
//
//        // Update the display of your TableView with the filtered data
//        tableView.setItems(filteredData);
//
//    }

    @FXML
    private void refreshData() {
        ProductData.getInstance().refreshData();
        populateTableValues();
        ErrorTool.showAlert("Data Refreshed", "Data has been refreshed and table repopulated");
    }
    // TODO: 2/21/2024
    @FXML
    private void newRow() {
        ProductData.getInstance().addProduct(new Product(123,"Brand","Name",new ArrayList<String>(),"type" ));
    }
    // TODO: 2/21/2024
    @FXML
    private void editRow() {
        ProductData.getInstance().updateProduct(new Product(123,"DifferentBrand","DifferentName",new ArrayList<String>(),"differenttype" ));
    }
    // TODO: 2/21/2024
    @FXML
    private void deleteRow() {
        ProductData.getInstance().deleteProduct(1);
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
    public void clearConnection() {
        DBConfig.clearProperties();
        ErrorTool.showAlert("Connection", "Connection information cleared");
    }
}
