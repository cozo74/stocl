package nju.ics.model.uml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UMLClass {
    String className;


    Map<String, String> attrList = new HashMap<>();

    public UMLClass(String className) {
        this.className = className;
    }

    public UMLClass(String className, Map<String, String> attrList) {
        this.className = className;
        this.attrList = attrList;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, String> getAttrList() {
        return attrList;
    }

    public void setAttrList(Map<String, String> attrList) {
        this.attrList = attrList;
    }


    public boolean hasAttr(String attrName) {
        return attrList.containsKey(attrName);
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
