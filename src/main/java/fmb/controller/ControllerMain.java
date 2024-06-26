package fmb.controller;

import fmb.model.PCategory;
import fmb.model.PType;
import fmb.model.Product;
import fmb.serviceData.CategoryData;
import fmb.serviceData.ProductData;
import fmb.serviceData.TypeData;
import fmb.tools.DBConfig;
import fmb.tools.ErrorTool;
import fmb.tools.ScraperTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerMain implements Initializable {
    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableView<PCategory> categoryTableView;

    @FXML
    private TableView<PType> typeTableView;

    @FXML
    private ChoiceBox<String> searchChoiceBox;

    private final String[] OPTIONS = {"ID", "Name", "Brand"};

    @FXML
    private TextField searchTextField;

    private static Product currentRow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ChoiceBox init
        searchChoiceBox.getItems().addAll(OPTIONS);
        searchChoiceBox.setValue("ID");

        //SearchBar eventlistener
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTableView();
        });
        //ChoiceBox eventListener
        searchChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateTableView();
        });

        //Selected product listener
        productTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection !=null)
                currentRow = newSelection;
        });

        populateTableValues();
    }

    @FXML
    private void populateTableValues() {
        populateProductTableView();
        populateCategoryTableView();
        populateTypeTableView();
    }

    @FXML
    private void populateProductTableView() {
        // Clear TableView
        productTableView.getItems().clear();
        productTableView.getColumns().clear();

        TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        idColumn.setPrefWidth(25);
        idColumn.setId("ID");

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        nameColumn.setId("Name");

        TableColumn<Product, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("productBrand"));
        brandColumn.setId("Brand");

        TableColumn<Product, Integer> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
        typeColumn.setPrefWidth(25);
        typeColumn.setId("Type");

        TableColumn<Product, Integer> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
        categoryColumn.setPrefWidth(25);
        categoryColumn.setId("Category");

        TableColumn<Product, String[]> ingredientsColumn = new TableColumn<>("Ingredients");
        ingredientsColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        ingredientsColumn.setId("Ingredients");
        ingredientsColumn.setPrefWidth(250);

        productTableView.getColumns().addAll(idColumn, nameColumn, brandColumn, typeColumn, categoryColumn, ingredientsColumn);

        // Populate TableView with data
        ArrayList<Product> instance = ProductData.getInstance().getAll();
        ObservableList<Product> products = FXCollections.observableArrayList(
                instance
        );
        productTableView.setItems(products);
    }

    public void populateCategoryTableView() {
        categoryTableView.getItems().clear();
        categoryTableView.getColumns().clear();

        TableColumn<PCategory, Integer> idColumn = new TableColumn<>("CID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("categoryID"));

        TableColumn<PCategory, String> categoryNameColumn = new TableColumn<>("Category name");
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        categoryTableView.getColumns().addAll(idColumn, categoryNameColumn);

        // Populate TableView with data
        ArrayList<PCategory> instance = CategoryData.getInstance().getAll();
        ObservableList<PCategory> categories = FXCollections.observableArrayList(
                instance
        );

        categoryTableView.setItems(categories);
    }

    public void populateTypeTableView() {
        typeTableView.getItems().clear();
        typeTableView.getColumns().clear();

        TableColumn<PType, Integer> idColumn = new TableColumn<>("TID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("typeID"));

        TableColumn<PType, Integer> typeCategoryIdColumn = new TableColumn<>("Category");
        typeCategoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("categoryID"));

        TableColumn<PType, String> typeNameColumn = new TableColumn<>("Type");
        typeNameColumn.setCellValueFactory(new PropertyValueFactory<>("typeName"));

        typeTableView.getColumns().addAll(idColumn, typeCategoryIdColumn, typeNameColumn);

        // Populate TableView with data
        ArrayList<PType> instance = TypeData.getInstance().getAll();
        ObservableList<PType> types = FXCollections.observableArrayList(
                instance
        );

        typeTableView.setItems(types);
    }

    @FXML
    private void updateTableView() {
        String searchQuery = searchTextField.getText().toLowerCase(); // Get search query from search bar input
        String selectedOption = searchChoiceBox.getValue(); // Get selected search option from choice box

        // Filter the data in your TableView based on the search query and selected search option
        ArrayList<Product> currentList = ProductData.getInstance().getAll();
        List<Product> filteredList = currentList.stream()
                .filter(product -> {
                    // Convert product details to lowercase for case-insensitive search
                    String fieldValue
                            = switch (selectedOption) {
                        case "ID" -> String.valueOf(product.getProductID());
                        case "Name" -> product.getProductName().toLowerCase();
                        case "Brand" -> product.getProductBrand().toLowerCase();
                        default -> "";
                    };
                    return fieldValue.contains(searchQuery);
                }).collect(Collectors.toList());

        // Convert the List to an ObservableList
        ObservableList<Product> filteredData = FXCollections.observableArrayList(filteredList);

        // Update the display of your TableView with the filtered data
        productTableView.setItems(filteredData);

    }

    @FXML
    private void refreshData() {
        try {
            ProductData.getInstance().refreshData();
            CategoryData.getInstance().refreshData();
            TypeData.getInstance().refreshData();
        } catch (Exception e) {
            ErrorTool.showAlert("DATABASE ERROR", "Properties file error: connection expected different url OR username OR password");
        }
        populateTableValues();
        ErrorTool.showAlert("Data Refreshed", "Data has been refreshed and table repopulated");
    }

    @FXML
    private void newRow() {
        try {
            //load popup view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/view/NewRowPopUp.fxml"));
            Parent root = loader.load();
            //set respective controller
            NewRowController controller = loader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Create new entry");
            stage.setScene(new Scene(root));
            controller.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Product getCurrentRow() {
        return currentRow;
    }

    @FXML
    private void editRow() {
        if (currentRow == null) return;

        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/view/EditRowPopUp.fxml"));
                Parent root = loader.load();
                EditRowController controller = loader.getController();
                Stage stage = new Stage();
                controller.setStage(stage);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Edit Entry");
                stage.setScene(new Scene(root));
                stage.showAndWait();
                } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void deleteRow() {
        // Get the selected item from the TableView
        Product selectedProduct = currentRow;

        // Check if an item is selected
        if (selectedProduct != null) {
            ProductData.getInstance().delete(selectedProduct.getProductID());
        }
        ErrorTool.showAlert("Delete Row", "This row was deleted. Please refresh to reflect changes");
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
            stage.setTitle("Connection setup");
            stage.setScene(new Scene(root));
            //Setup to close popup by using stage.close within alert. See ShowAlertAndClose
            controller.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void newRowBulk() throws IOException {
        ArrayList<String> itemsList = ScraperTool.getBrandSoup("");
        for (String s :
                itemsList) {
            Product p = ScraperTool.getItemSoup("https://incidecoder.com"+s);
            ProductData.getInstance().add(p);
        }
    }

    @FXML
    public void clearConnection() {
        DBConfig.clearProperties();
        ErrorTool.showAlert("Connection", "Connection information cleared");
    }
}
