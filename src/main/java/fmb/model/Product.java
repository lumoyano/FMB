package fmb.model;

import java.util.ArrayList;

public class Product {
    private int productID;
    private String productBrand;
    private String productName;
    private ArrayList<String> ingredients;
    private String productType;

    public Product(int productID, String productBrand, String productName, ArrayList<String> ingredients, String productType) {
        this.productID = productID;
        this.productBrand = productBrand;
        this.productName = productName;
        this.ingredients = ingredients;
        this.productType = productType;
    }

    public Product() {
    }

    public int getProductID() {
        return productID;
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

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}
