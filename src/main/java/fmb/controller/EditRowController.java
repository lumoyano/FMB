package fmb.controller;

import fmb.model.Product;
import fmb.serviceData.ProductData;
import fmb.tools.ErrorTool;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class EditRowController {

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productBrandField;

    @FXML
    private TextField productTypeField;

    @FXML
    private TextArea ingredientsArea;

    @FXML
    private Stage stage;

    @FXML
    public void saveChanges() {
        String[] individualIngs = ingredientsArea.getText().split("\\s*,\\s*");
        ArrayList<String> ingredients = new ArrayList<>(Arrays.asList(individualIngs));

        //guard clauses for no ID and for empty fields
        if (ProductData.getInstance().getNextID() == -1) {
            ErrorTool.showAlertAndClose("Error", "There was an error creating your entry: Next available ID returned -1", stage);
            return;
        }
        if (productBrandField.getText().isEmpty() ||
                productNameField.getText().isEmpty() ||
                productTypeField.getText().isEmpty() ||
                ingredientsArea.getText().isEmpty()){
            ErrorTool.showAlert("Error", "There was an error creating your entry. Please make sure all fields are filled.");
            return;
        }
        Product newRow = new Product(ProductData.getInstance().getNextID(),
                productBrandField.getText(),
                productNameField.getText(),
                ingredients,
                productTypeField.getText());
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
}
