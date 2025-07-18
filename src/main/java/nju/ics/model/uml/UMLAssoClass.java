package nju.ics.model.uml;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UMLAssoClass {
    String assoClassName, startClass, endClass, role, r_role, multi, r_multi ;
    boolean hasAttrs = false;
    Map<String, String> attrList = new HashMap<>();

    public UMLAssoClass(String assoClassName, String startClass, String endClass, String role, String r_role, String multi, String r_multi) {
        this.assoClassName = assoClassName;
        this.startClass = startClass;
        this.endClass = endClass;
        this.role = role;
        this.r_role = r_role;
        this.multi = multi;
        this.r_multi = r_multi;
    }

    public UMLAssoClass(String assoClassName, String startClass, String endClass, String role, String r_role, String multi, String r_multi, boolean hasAttrs, Map<String, String> attrList) {
        this.assoClassName = assoClassName;
        this.startClass = startClass;
        this.endClass = endClass;
        this.role = role;
        this.r_role = r_role;
        this.multi = multi;
        this.r_multi = r_multi;
        this.hasAttrs = hasAttrs;
        this.attrList = attrList;
    }

    public String getAssoClassName() {
        return assoClassName;
    }

    public void setAssoClassName(String assoClassName) {
        this.assoClassName = assoClassName;
    }

    public String getStartClass() {
        return startClass;
    }

    public void setStartClass(String startClass) {
        this.startClass = startClass;
    }

    public String getEndClass() {
        return endClass;
    }

    public void setEndClass(String endClass) {
        this.endClass = endClass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getR_role() {
        return r_role;
    }

    public void setR_role(String r_role) {
        this.r_role = r_role;
    }

    public String getMulti() {
        return multi;
    }

    public void setMulti(String multi) {
        this.multi = multi;
    }

    public String getR_multi() {
        return r_multi;
    }

    public void setR_multi(String r_multi) {
        this.r_multi = r_multi;
    }

    public boolean isHasAttrs() {
        return hasAttrs;
    }

    public void setHasAttrs(boolean hasAttrs) {
        this.hasAttrs = hasAttrs;
    }

    public Map<String, String> getAttrList() {
        return attrList;
    }

    public void setAttrList(Map<String, String> attrList) {
        this.attrList = attrList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UMLAssoClass that = (UMLAssoClass) o;
        return Objects.equals(assoClassName, that.assoClassName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(assoClassName);
    }

    @Override
    public String toString() {
        return "UMLAssoClass{" +
                "assoClassName='" + assoClassName + '\'' +
                '}';
    }
}
