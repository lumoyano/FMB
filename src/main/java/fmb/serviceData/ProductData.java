package fmb.serviceData;

import fmb.model.Product;
import fmb.tools.DBConfig;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class ProductData {
    private static ArrayList<Product> currentList = new ArrayList<>();

    /*
    Full link has a format jdbc:postgresql://server:port5432/defaultdatabase?userparam&passwdparam
    ElephantSQL provides a link that is both incorrect and misspelled, Use this as a rule
     */
    private final String URL = DBConfig.getDatabaseUrl(); //jdbc:postgresql://server:port5432/defaultdatabase
    private final String USERNAME = DBConfig.getDBUsername();
    private final String PASSWORD = DBConfig.getDBPassword();

    private static ProductData instance = new ProductData();

    private ProductData() {}

    public static ProductData getInstance() {
        return instance;
    }

    public ArrayList<Product> getCurrentList() {
        return currentList;
    }

    public void refreshData() {
        loadDataFromDB();
    }

    private static void loadDataFromDB() {


        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try (Connection db = DriverManager.getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
             Statement st = db.createStatement()) {

            ResultSet resultSet = st.executeQuery("SELECT * FROM PRODUCT");
            currentList.clear();
            while (resultSet.next()) {
                //get all fields
                int productID = resultSet.getInt("productid");
                System.out.println(productID);
                String productName = resultSet.getString("productname");
                System.out.println(productName);
                String productType = resultSet.getString("producttype");
                System.out.println(productType);
                String productBrand = resultSet.getString("productbrand");
                System.out.println(productBrand);
                String[] ingredientsArray = new String[]{resultSet.getString("ingredients")};
                System.out.println(ingredientsArray.toString());
                //Parse ingredients into an arraylist
                ArrayList<String> ingredients = new ArrayList<>(); //this is the item used for product constructor
                for (String s :
                        ingredientsArray) {
                    ingredients.add(s.trim());
                }
                System.out.println(ingredients);
                //create new product with all fields
                Product currentRow = new Product(productID, productBrand, productName, ingredients, productType);
                currentList.add(currentRow);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProduct(Product product) { //Unfinished method
        try (Connection db = DriverManager
                .getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
             PreparedStatement statement = db.prepareStatement("INSERT INTO products (product_name) VALUES (?)")) {

            statement.setString(1, product.getProductName());
            // Set other parameters...
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) { //Unfinished method
        try (Connection db = DriverManager
                .getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
             PreparedStatement statement = db.prepareStatement("UPDATE products SET product_name = ? WHERE product_id = ?")) {

            statement.setString(1, product.getProductName());
            // Set other parameters...
            statement.setInt(2, product.getProductID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        try (Connection db = DriverManager
                    .getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
            PreparedStatement statement = db.prepareStatement("DELETE FROM products WHERE product_id = ?")) {

            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
