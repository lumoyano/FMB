package fmb.serviceData;

import fmb.model.PType;
import fmb.tools.DBConfig;
import fmb.tools.ErrorTool;

import java.sql.*;
import java.util.ArrayList;

public class TypeData implements DataAccessObject<PType> {
    private ArrayList<PType> currentList = new ArrayList<>();

    /*
    Full link has a format jdbc:postgresql://server:port5432/defaultdatabase?userparam&passwdparam
    ElephantSQL provides a link that is both incorrect and misspelled, Use this as a rule
     */
    private String URL = DBConfig.getDatabaseUrl(); //jdbc:postgresql://server:port5432/defaultdatabase
    private String USERNAME = DBConfig.getDBUsername();
    private String PASSWORD = DBConfig.getDBPassword();

    private static TypeData instance = new TypeData();

    private TypeData() {}

    public static TypeData getInstance() {
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

    private void loadDataFromDB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        currentList.clear();

        try (Connection db = DriverManager.getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
             Statement st = db.createStatement()) {

            ResultSet resultSet = st.executeQuery("SELECT * FROM TYPES");
            while (resultSet.next()) {
                //get all fields
                int typeID = resultSet.getInt("typeid");
                int categoryID = resultSet.getInt("categoryid");
                String typeName = resultSet.getString("typename");

                PType currentRow = new PType(typeID, categoryID, typeName);
                currentList.add(currentRow);
            }
        } catch (SQLException e) {
            ErrorTool.showAlert("DATABASE ERROR", "Properties file error: connection expected different url OR username OR password");
        }
    }

    @Override
    public ArrayList<PType> getAll() {
        return currentList;
    }

    @Override
    public int getNextID() {
        try (Connection db = DriverManager.getConnection(DBConfig.getDatabaseUrl(), DBConfig.getDBUsername(), DBConfig.getDBPassword());
             PreparedStatement statement = db.prepareStatement("SELECT MAX(typeid) AS max_id FROM types");
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
    public void add(PType type) {
        try (Connection db = DriverManager.getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
             PreparedStatement statement = db.prepareStatement("INSERT INTO types (typeid, typename, categoryid) " +
                     "VALUES (?, ?, ?)")) {

            statement.setInt(1, type.getTypeID());
            statement.setString(2, type.getTypeName());
            statement.setInt(3, type.getCategoryID());

            // Execute the SQL statement
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(PType type){
        try (Connection db = DriverManager.getConnection(getInstance().URL, getInstance().USERNAME, getInstance().PASSWORD);
             PreparedStatement statement = db.prepareStatement("UPDATE types SET typename=?, categoryid=? " +
                     "WHERE typeid=?")) {

            statement.setString(1, type.getTypeName());
            statement.setInt(2, type.getCategoryID());

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
             PreparedStatement statement = db.prepareStatement("DELETE FROM types WHERE typeid = ?")) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<PType> getTypesByCategory(int queryID) {
        ArrayList<PType> matchedTypes = new ArrayList<>();
        for (PType p :
                currentList) {
            if (p.getCategoryID() == queryID)
                matchedTypes.add(p);
        }
        return matchedTypes;
    }

    public PType getTypeByID(int id) {
        for (PType p :
                currentList) {
            if (p.getTypeID() == id)
                return p;
        }
        return null;
    }
}
