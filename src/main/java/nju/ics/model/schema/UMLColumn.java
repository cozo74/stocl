package nju.ics.model.schema;

public class UMLColumn {

    private String name;


    private String type; // e.g., "INTEGER", "VARCHAR", etc.

    public UMLColumn(String name, String type) {
        this.name = name;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }


}
