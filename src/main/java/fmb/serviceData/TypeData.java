package fmb.serviceData;

import fmb.model.PType;
import fmb.tools.DBConfig;
import fmb.tools.ErrorTool;

import java.sql.*;
import java.util.ArrayList;

public class TypeData {
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

    public ArrayList<PType> getCurrentList() {
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
