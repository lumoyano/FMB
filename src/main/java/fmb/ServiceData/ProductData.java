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

    private void loadDataFromDB() {
            String url = DBConfig.getDatabaseUrl();
            String username = DBConfig.getDBUsername();
            String password = DBConfig.getDBPassword();

            try {
                Connection db = DriverManager.getConnection(url, username, password);
                Statement st = db.createStatement();
                ResultSet resultSet = st.executeQuery("SELECT * FROM PRODUCT");
                while (resultSet.next()) {
                    /*
                    column_name	data_type
                    productid	integer
                    productbrand	integer
                    productname	character varying
                    ingredients	text
                    producttype	character varying
                    */
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}
