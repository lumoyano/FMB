package fmb.serviceData;

import fmb.model.PCategory;
import fmb.tools.DBConfig;
import fmb.tools.ErrorTool;

import java.sql.*;
import java.util.ArrayList;

public class CategoryData implements DataAccessObject<PCategory> {
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


    @Override
    public ArrayList<PCategory> getAll() {
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

    @Override
    public int getNextID() {
        try (Connection db = DriverManager.getConnection(DBConfig.getDatabaseUrl(), DBConfig.getDBUsername(), DBConfig.getDBPassword());
             PreparedStatement statement = db.prepareStatement("SELECT MAX(categoryid) AS max_id FROM categories");
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
    public void add(PCategory category) {
        try (Connection db = DriverManager.getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
             PreparedStatement statement = db.prepareStatement("INSERT INTO categories (categoryid, categoryname) " +
                     "VALUES (?, ?)")) {

            statement.setInt(1, category.getCategoryID());
            statement.setString(2, category.getCategoryName());

            // Execute the SQL statement
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(PCategory category) {
        try (Connection db = DriverManager.getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
             PreparedStatement statement = db.prepareStatement("UPDATE types SET categoryname=?" +
                     "WHERE categoryid=?")) {

            statement.setString(1, category.getCategoryName());
            statement.setInt(2, category.getCategoryID());

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
    public void delete(int id) {
        try (Connection db = DriverManager
                .getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
             PreparedStatement statement = db.prepareStatement("DELETE FROM categories WHERE categoryid = ?")) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PCategory getCategoryByID (int queryID) {
        for ( PCategory p :
                currentList) {
            if (p.getCategoryID() == queryID)
                return p;
        }
        return null;
    }
}
