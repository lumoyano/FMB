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
//                    int productId = resultSet.getInt("productid");
//                    String productName = resultSet.getString("productname");
//                    String productType = resultSet.getString("producttype");
//                    String productBrand = resultSet.getString("productbrand");
//                    Array ingredientsArray = resultSet.getArray("ingredients");
//                    String[] ingredients = (String[]) ingredientsArray.getArray();
                    System.out.println(resultSet.toString());

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

}
