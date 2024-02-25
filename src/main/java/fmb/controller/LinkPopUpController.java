package fmb.controller;

import fmb.serviceData.ScraperData;
import fmb.tools.ErrorTool;
import fmb.tools.ScraperTool;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LinkPopUpController implements Initializable {
    @FXML
    private Stage stage;

    @FXML
    private TextField linkTextField;

    @FXML
    private TextArea outputTextArea;

    private ArrayList<String> expectedIngredients = new ArrayList<String>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        linkTextField.textProperty().addListener( (observable, oldValue, newValue) -> {
            try {
                expectedIngredients = ScraperTool.getSoup(linkTextField.getText());
            } catch (IOException e) {
                return;
            }
            // Populate text area with ingredients
            StringBuilder ingredientsText = new StringBuilder();
            for (String ingredient : expectedIngredients) {
                ingredientsText.append(ingredient).append(",\n"); // Append each ingredient with a newline
            }
            outputTextArea.setText(ingredientsText.toString());
        });
    }

    @FXML
    public void insert() {
        if (expectedIngredients.isEmpty()) {
            ErrorTool.showAlert("Error", "Please enter a valid link to scrape.");
            return;
        }
        ScraperData.getInstance().setScrapedIngredients(expectedIngredients);
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
