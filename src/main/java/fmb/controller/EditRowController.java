package fmb.controller;

import fmb.model.Product;
import fmb.serviceData.ProductData;
import fmb.serviceData.ScraperData;
import fmb.tools.ErrorTool;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class EditRowController implements Initializable {

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

    private Product product;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        product = ControllerMain.getCurrentRow();
        populateFields();

    }

    @FXML
    public void saveChanges() {
        String[] individualIngs = ingredientsArea.getText().split("\\s*,\\s*");
        ArrayList<String> ingredients = new ArrayList<>(Arrays.asList(individualIngs));

        if (productBrandField.getText().isEmpty() ||
                productNameField.getText().isEmpty() ||
                productTypeField.getText().isEmpty() ||
                ingredientsArea.getText().isEmpty()){
            ErrorTool.showAlert("Error", "There was an error changing your entry. Please make sure all fields are filled.");
            return;
        }
        Product newRow = new Product(ControllerMain.getCurrentRow().getProductID(),
                productBrandField.getText(),
                productNameField.getText(),
                productTypeField.getText(),
                ingredients);
        try{
            ProductData.getInstance().updateProduct(newRow);
            ErrorTool.showAlertAndClose("Edit Row",
                    "Changes to row were made. Please refresh to see changes", stage);
        } catch (Exception e) {
            ErrorTool.showAlert("Error", "There was an error changing your entry.");
        }

    }

    public void populateFields() {
        if (product == null) return;
        // Populate text fields with product data
        productNameField.setText(product.getProductName());
        productBrandField.setText(product.getProductBrand());
        productTypeField.setText(product.getProductType());

        // Populate text area with ingredients from link if present, and by product default
        StringBuilder ingredientsText = new StringBuilder();
        if (ScraperData.getInstance().getScrapedIngredients().isEmpty()){
            for (String ingredient :
                    product.getIngredients()) {
                ingredientsText.append(ingredient).append(",\n"); // Append each ingredient with a newline
            }
        } else {
            for (String ingredient :
                    ScraperData.getInstance().getScrapedIngredients()) {
                ingredientsText.append(ingredient).append(",\n");
            }
            ScraperData.getInstance().clearScrapedIngredients();
        }
        ingredientsArea.setText(ingredientsText.toString());
    }

    @FXML
    public void setStage(Stage s) {
        this.stage = s;
    }

    @FXML
    public void openScrapeView() {
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

    public void setProduct(Product product) {
        this.product = product;
    }
}
