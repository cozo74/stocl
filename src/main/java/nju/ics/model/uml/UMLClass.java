package nju.ics.model.uml;


import java.util.*;

import nju.ics.grammar.stocl.PrimitiveType;

public class UMLClass {
    private String className;

    final static Map<String, PrimitiveType> legalType = Map.of(
            "Integer", PrimitiveType.INTEGER,
            "Real", PrimitiveType.REAL,
            "Boolean", PrimitiveType.BOOLEAN,
            "String", PrimitiveType.STRING,
            "Geometry", PrimitiveType.GEOMETRY,
            "Timestamp", PrimitiveType.TIMESTAMP
    );

    //   attribute name, type
    private Map<String, String> attrMap = new HashMap<>();

    private List<String> attrList = new ArrayList<>();

    public UMLClass(String className) {
        this.className = className;
    }

    public UMLClass(String className, List<String> attrList, Map<String, String> attrMap) {
        this.className = className;
        this.attrList = attrList;
        this.attrMap = attrMap;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getAttrList() {
        return attrList;
    }

    public Map<String, String> getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(Map<String, String> attrMap) {
        this.attrMap = attrMap;
    }


    public boolean hasAttr(String attrName) {
        return attrMap.containsKey(attrName);
    }

    public boolean isAttrTypeLegal(String attrName) {
        return legalType.containsKey(attrMap.get(attrName));
    }

    public PrimitiveType getAttrType(String attrName){

        if (!hasAttr(attrName) && !isAttrTypeLegal(attrName)) {
            throw new RuntimeException("Attribute " + attrName + " does not exist or has illegal type in class " + className);
        }
        return legalType.get(attrMap.get(attrName));

    }

    public String getAttrTypeString(String attrName){

        if (!hasAttr(attrName) && !isAttrTypeLegal(attrName)) {
            throw new RuntimeException("Attribute " + attrName + " does not exist or has illegal type in class " + className);
        }
        return attrMap.get(attrName);

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UMLClass umlClass = (UMLClass) o;
        return Objects.equals(className, umlClass.className);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(className);
    }


    @Override
    public String toString() {
        return "UMLClass{" +
                "className='" + className + '\'' +
                '}';
    }


}
