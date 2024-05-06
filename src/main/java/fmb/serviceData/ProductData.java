package fmb.serviceData;

import fmb.model.Product;
import fmb.tools.DBConfig;
import fmb.tools.ErrorTool;
import javafx.fxml.FXML;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ProductData implements DataAccessObject<Product> {
    private static ArrayList<Product> currentList = new ArrayList<>();

    /*
    Full link has a format jdbc:postgresql://server:port5432/defaultdatabase?userparam&passwdparam
    ElephantSQL provides a link that is both incorrect and misspelled, Use this as a rule
     */
    private String URL = DBConfig.getDatabaseUrl(); //jdbc:postgresql://server:port5432/defaultdatabase
    private String USERNAME = DBConfig.getDBUsername();
    private String PASSWORD = DBConfig.getDBPassword();

    private static ProductData instance = new ProductData();

    private ProductData() {}

    public static ProductData getInstance() {
        return instance;
    }

    public void refreshData() {
        refreshProperties();
        loadDataFromDB();
    }

    private void refreshProperties() {
        URL = DBConfig.getDatabaseUrl(); //jdbc:postgresql://server:port5432/defaultdatabase
        USERNAME = DBConfig.getDBUsername();
        PASSWORD = DBConfig.getDBPassword();
    }

    private static void loadDataFromDB() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        currentList.clear();

        try (Connection db = DriverManager.getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
             Statement st = db.createStatement()) {

            ResultSet resultSet = st.executeQuery("SELECT * FROM PRODUCTS");
            while (resultSet.next()) {
                //get all fields
                int productID = resultSet.getInt("productid");
                String productName = resultSet.getString("productname");
                String productBrand = resultSet.getString("productbrand");
                int productType = resultSet.getInt("producttype");
                int productCategory = resultSet.getInt("productcategory");
                String ingredientsString = resultSet.getString("ingredients");
                //convert string to arraylist
                ArrayList<String> ingredients = new ArrayList<>(Arrays.asList(ingredientsString.split(", ")));
                //create new product with all fields
                Product currentRow = new Product(productID, productName, productBrand, productType, productCategory, ingredients);
                currentList.add(currentRow);
            }
        } catch (SQLException e) {
            //handled when called
        }
    }

    @Override
    public ArrayList<Product> getAll() {
        return currentList;
    }

    @Override
    public int getNextID() {
        try (Connection db = DriverManager.getConnection(DBConfig.getDatabaseUrl(), DBConfig.getDBUsername(), DBConfig.getDBPassword());
             PreparedStatement statement = db.prepareStatement("SELECT MAX(productid) AS max_id FROM products");
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                int maxId = resultSet.getInt("max_id");
                return maxId + 1; // Increment the max ID to get the next available ID
            } else {
                return 1; // If no records found, start from 1
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Error occurred, return -1 as indication
        }
    }

    @Override
    public void add(Product product) {
        try (Connection db = DriverManager.getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
             PreparedStatement statement = db.prepareStatement("INSERT INTO products (productid, productname, " +
                     "productbrand, producttype, productcategory, ingredients) VALUES (?, ?, ?, ?, ?, ?)")) {

            statement.setInt(1, product.getProductID());
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getProductBrand());
            statement.setInt(4, product.getProductType());
            statement.setInt(5, product.getProductCategory());

            // Convert ingredients ArrayList to a single string with comma-separated values
            statement.setString(6, product.getIngredientsAsString());

            // Execute the SQL statement
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product product) {
        try (Connection db = DriverManager.getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
                 PreparedStatement statement = db.prepareStatement("UPDATE products SET productname=?, productbrand=?, " +
                         "producttype=?, productcategory=?, ingredients=? WHERE productid=?")) {

            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductBrand());
            statement.setInt(3, product.getProductType());
            statement.setInt(4, product.getProductCategory());
            statement.setString(5, product.getIngredientsAsString());
            statement.setInt(6, product.getProductID());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Failed to update product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int productId) {
        try (Connection db = DriverManager
                    .getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
            PreparedStatement statement = db.prepareStatement("DELETE FROM products WHERE productid = ?")) {

            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
