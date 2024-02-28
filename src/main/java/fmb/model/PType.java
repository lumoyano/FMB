package fmb.model;

public class PType {
    private int typeID;

    private int categoryID;

    private String typeName;

    public PType(int typeID, int categoryID, String typeName) {
        this.typeID = typeID;
        this.categoryID = categoryID;
        this.typeName = typeName;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
