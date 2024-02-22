package fmb.serviceData;

import fmb.controller.ControllerMain;
import fmb.model.Product;
import fmb.tools.DBConfig;
import fmb.tools.ErrorTool;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class ProductData {
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

    public ArrayList<Product> getCurrentList() {
        return currentList;
    }

    public void refreshData() {
        refreshProperties();
        loadDataFromDB();
    }

    public void refreshProperties() {
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
            ErrorTool.showAlert("DATABASE ERROR", "Properties file error: connection expected different url OR username OR password");
        }
    }

    public void addProduct(Product product) { //Unfinished method
        try (Connection db = DriverManager
                .getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
            PreparedStatement statement = db.prepareStatement("INSERT INTO products (productid, productname, producttype, productbrand,ingredients) VALUES (?, ?, ?, ?, ?)")){

            statement.setInt(1, product.getProductID());
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getProductType());
            // Assuming ingredients is a String array, you need to convert it to a string representation
            statement.setString(4, product.getProductBrand());
            ArrayList<String> ingredientsString = product.getIngredients();
            statement.setString(5, String.valueOf(ingredientsString));

            System.out.println(statement);

//            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        try (Connection db = DriverManager.getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
             PreparedStatement statement = db.prepareStatement("UPDATE products SET productname = ?, producttype = ?, productbrand = ?, ingredients = ? WHERE productid = ?")) {

            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductType());
            statement.setString(3, product.getProductBrand());
            // Assuming ingredients is a String array, you need to convert it to a string representation
            ArrayList<String> ingredientsString = product.getIngredients();
            statement.setString(4, String.valueOf(ingredientsString));
            statement.setInt(5, product.getProductID());

            System.out.println(statement);

           // statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        try (Connection db = DriverManager
                    .getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
            PreparedStatement statement = db.prepareStatement("DELETE FROM products WHERE product_id = ?")) {

            System.out.println(statement);

            statement.setInt(1, productId);
//            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}