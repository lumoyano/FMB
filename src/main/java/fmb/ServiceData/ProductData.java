package fmb.ServiceData;

import fmb.model.Product;
import fmb.tools.DBConfig;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
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

    private static void loadDataFromDB() {
        /*
        Full link has a format jdbc:postgresql://server:port5432/defaultdatabase?userparam&passwdparam
        ElephantSQL provides a link that is both incorrect and misspelled, Use this as a rule
         */
        String url = DBConfig.getDatabaseUrl(); //jdbc:postgresql://server:port5432/defaultdatabase
        String username = DBConfig.getDBUsername();
        String password = DBConfig.getDBPassword();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection db = DriverManager.getConnection(url, username, password);
            Statement st = db.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM PRODUCT");
            while (resultSet.next()) {
                //get all fields
                int productId = resultSet.getInt("productid");
                System.out.println(productId);
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
