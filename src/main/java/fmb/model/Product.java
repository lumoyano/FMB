package fmb.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Product {
    private int productID;
    private String productName;
    private String productBrand;
    private int productType;
    private int productCategory;
    private ArrayList<String> ingredients;

    public Product(int productID, String productName, String productBrand, int productType, int productCategory,  ArrayList<String> ingredients) {
        this.productID = productID;
        this.productBrand = productBrand;
        this.productName = productName;
        this.productType = productType;
        this.productCategory = productCategory;
        this.ingredients = ingredients;
    }

    public int getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(int productCategory) {
        this.productCategory = productCategory;
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

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
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
