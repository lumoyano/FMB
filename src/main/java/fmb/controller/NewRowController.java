package fmb.controller;

import fmb.model.Product;
import fmb.serviceData.ProductData;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;

public class NewRowController {

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productBrandField;

    @FXML
    private TextField productTypeField;

    @FXML
    private TextArea ingredientsArea;

    @FXML
    public void saveChanges() {
        String[] individualIngs = ingredientsArea.getText().split("\\s*,\\s*");
        ArrayList<String> ingredients = new ArrayList<>(Arrays.asList(individualIngs));
        Product newRow = new Product(ProductData.getInstance().getNextID(),
                productBrandField.getText(),
                productNameField.getText(),
                ingredients,
                productTypeField.getText());
        ProductData.getInstance().addProduct(newRow);
    }
}
