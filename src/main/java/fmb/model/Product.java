package fmb.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Product {
    private int productID;
    private String productBrand;
    private String productName;
    private ArrayList<String> ingredients;
    private String productType;

    public Product(int productID, String productBrand, String productName, String productType, ArrayList<String> ingredients) {
        this.productID = productID;
        this.productBrand = productBrand;
        this.productName = productName;
        this.productType = productType;
        this.ingredients = ingredients;
    }

    public Product() {
    }

    public int getProductID() {
        return productID;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", productBrand='" + productBrand + '\'' +
                ", productName='" + productName + '\'' +
                ", ingredients=" + ingredients +
                ", productType='" + productType + '\'' +
                '}';
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getIngredientsAsString() {
        return String.join(", ", ingredients);
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setIngredientsFromString (String ingredientsString ) {
        this.ingredients = new ArrayList<>(Arrays.asList(ingredientsString.split(", ")));
    }
}
