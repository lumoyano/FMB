package fmb.controller;

import fmb.model.PCategory;
import fmb.model.PType;
import fmb.model.Product;
import fmb.serviceData.CategoryData;
import fmb.serviceData.ProductData;
import fmb.serviceData.ScraperData;
import fmb.serviceData.TypeData;
import fmb.tools.ErrorTool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class NewRowController implements Initializable {

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productBrandField;

    @FXML
    private ChoiceBox<String> productCategoryChoiceBox;

    private String[] categoryOptions;

    @FXML
    private ChoiceBox<String> productTypeChoiceBox;

    private ArrayList<String> typeOptions = new ArrayList<>();

    @FXML
    private TextArea ingredientsArea;

    @FXML
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCategories();
        populateFields();

        productCategoryChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.equals(newValue)) return;
            typeOptions.clear();
            productTypeChoiceBox.setValue("");
            String[] parts = newValue.split(",");
            int id = Integer.parseInt(parts[0]);
            ArrayList<PType> types = TypeData.getInstance().getTypesByCategory(id);
            for (PType p :
                    types) {
                typeOptions.add(p.getTypeID() + ", " +p.getTypeName());
            }

            productTypeChoiceBox.getItems().clear();
            productTypeChoiceBox.getItems().addAll(typeOptions);
        });
    }

    @FXML
    public void loadCategories() {
        //load category
        CategoryData.getInstance().refreshData();
        ArrayList<PCategory> categories = CategoryData.getInstance().getCurrentList();
        categoryOptions = new String[categories.size()];
        for (int i=0; i<categoryOptions.length; i++) {
            categoryOptions[i] = categories.get(i).getCategoryID() + ", "
                    + categories.get(i).getCategoryName();
        }

        //load types
        TypeData.getInstance().refreshData();
    }

    @FXML
    public void saveChanges() {
        String[] individualIngs = ingredientsArea.getText().split("\\s*,\\s*");
        ArrayList<String> ingredients = new ArrayList<>(Arrays.asList(individualIngs));

        //guard clauses for no ID and for empty fields
        if (ProductData.getInstance().getNextID() == -1) {
            ErrorTool.showAlertAndClose("Error", "There was an error creating your entry: Next available ID returned -1", stage);
            return;
        }
        if (productNameField.getText().isEmpty() ||
                productBrandField.getText().isEmpty() ||
                productCategoryChoiceBox.getValue().isEmpty() ||
                productTypeChoiceBox.getValue().isEmpty() ||
                ingredientsArea.getText().isEmpty()){
            ErrorTool.showAlert("Error", "There was an error creating your entry. Please make sure all fields are filled.");
            return;
        }

        String[] categoryParts = productCategoryChoiceBox.getValue().split(",");
        int category = Integer.parseInt(categoryParts[0]);

        String[] typeParts = productTypeChoiceBox.getValue().split(",");
        int type = Integer.parseInt(typeParts[0]);

        Product newRow = new Product(ProductData.getInstance().getNextID(),
                productNameField.getText(),
                productBrandField.getText(),
                type,
                category,
                ingredients);
        try{
            ProductData.getInstance().addProduct(newRow);
            ErrorTool.showAlertAndClose("New Row added",
                    "Succesfully added row. Please refresh to see changes", stage);
        } catch (Exception e) {
            ErrorTool.showAlert("Error", "There was an error creating your entry");
        }

    }

    @FXML
    public void setStage(Stage s) {
        this.stage = s;
    }

    public void openScrapeView(ActionEvent actionEvent) {
        try {
            //load popup view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/view/LinkPopUp.fxml"));
            Parent root = loader.load();
            //set respective controller
            LinkPopUpController controller = loader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Connection setup");
            stage.setScene(new Scene(root));
            //Setup to close popup by using stage.close within alert. See ShowAlertAndClose
            controller.setStage(stage);
            stage.showAndWait();
            populateFields();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateFields() {
        productCategoryChoiceBox.getItems().clear();
        productCategoryChoiceBox.getItems().addAll(categoryOptions);

        // Populate text area with ingredients from link if present
        StringBuilder ingredientsText = new StringBuilder();
        if (!ScraperData.getInstance().getScrapedIngredients().isEmpty()){
            for (String ingredient :
                    ScraperData.getInstance().getScrapedIngredients()) {
                ingredientsText.append(ingredient).append(",\n");
            }
            ScraperData.getInstance().clearScrapedIngredients();
            ingredientsArea.setText(ingredientsText.toString());
        }
    }
}
