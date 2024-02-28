package fmb.serviceData;

import fmb.model.PCategory;
import fmb.model.Product;
import fmb.tools.DBConfig;
import fmb.tools.ErrorTool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CategoryData {
    private ArrayList<PCategory> currentList = new ArrayList<>();

    /*
    Full link has a format jdbc:postgresql://server:port5432/defaultdatabase?userparam&passwdparam
    ElephantSQL provides a link that is both incorrect and misspelled, Use this as a rule
     */
    private String URL = DBConfig.getDatabaseUrl(); //jdbc:postgresql://server:port5432/defaultdatabase
    private String USERNAME = DBConfig.getDBUsername();
    private String PASSWORD = DBConfig.getDBPassword();

    private static CategoryData instance = new CategoryData();

    private CategoryData() {}

    public static CategoryData getInstance() {
        return instance;
    }

    public ArrayList<PCategory> getCurrentList() {
        return currentList;
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

    private void loadDataFromDB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        currentList.clear();

        try (Connection db = DriverManager.getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
             Statement st = db.createStatement()) {

            ResultSet resultSet = st.executeQuery("SELECT * FROM CATEGORIES");
            while (resultSet.next()) {
                //get all fields
                int categoryID = resultSet.getInt("categoryID");
                String categoryName = resultSet.getString("categoryName");

                PCategory currentRow = new PCategory(categoryID, categoryName);
                currentList.add(currentRow);
            }
        } catch (SQLException e) {
            ErrorTool.showAlert("DATABASE ERROR", "Properties file error: connection expected different url OR username OR password");
        }
    }
}
