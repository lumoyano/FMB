package fmb.model;

import java.util.ArrayList;

public class Product {
    private int productID;
    private String productName;
    private String productBrand;
    private String productType;
    private ArrayList<String> ingredients;

    public Product(int productID, String productName, String productBrand, String productType, ArrayList<String> ingredients) {
        this.productID = productID;
        this.productName = productName;
        this.productBrand = productBrand;
        this.productType = productType;
        this.ingredients = ingredients;
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
