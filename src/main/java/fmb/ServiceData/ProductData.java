package fmb.ServiceData;

import fmb.model.Product;

import java.util.ArrayList;

public class ProductData {
    private ArrayList<Product> currentList = new ArrayList<>();

    private static ProductData instance = new ProductData();

    private ProductData() {
        loadDataFromDB();
    }

    public static ProductData getInstance() {
        return instance;
    }

    public ArrayList<Product> getCurrentList() {
        return currentList;
    }

    public void refreshData() {
        loadDataFromDB();
    }

    private void loadDataFromDB() {

    }
}
