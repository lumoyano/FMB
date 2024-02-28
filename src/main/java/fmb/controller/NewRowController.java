package fmb.controller;

import fmb.model.Product;
import fmb.serviceData.ProductData;
import fmb.tools.ErrorTool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
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

    private String[] typeOptions;

    @FXML
    private TextArea ingredientsArea;

    @FXML
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateChoiceBox();
    }

    @FXML
    private void populateChoiceBox() {

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
        //TODO
        if (productNameField.getText().isEmpty() ||
                productBrandField.getText().isEmpty() ||
                productTypeField.getText().isEmpty() ||
                productBrandField.getText().isEmpty() ||
                ingredientsArea.getText().isEmpty()){
            ErrorTool.showAlert("Error", "There was an error creating your entry. Please make sure all fields are filled.");
            return;
        }
        Product newRow = new Product(ProductData.getInstance().getNextID(),
                productNameField.getText(),
                productBrandField.getText(),
                productTypeField.getText(),
                //TODO
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
    }
}
