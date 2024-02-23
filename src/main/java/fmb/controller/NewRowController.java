package fmb.controller;

import fmb.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
        Product newRow = new Product()
    }
}
