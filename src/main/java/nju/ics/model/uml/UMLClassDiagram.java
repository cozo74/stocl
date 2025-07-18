package nju.ics.model.uml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import nju.ics.grammar.oclcore.elements.OCLValueType;
import nju.ics.model.ra.Relation;
import nju.ics.model.ra.RelationSchema;

import org.antlr.v4.runtime.misc.Pair;


public class UMLClassDiagram {

    String name;
    Map<String, UMLClass> classes = new HashMap<>();
    Map<String, UMLAssoClass> assoClasses = new HashMap<>();


    Map<String, Map<String, Pair<String, String>>> classRoleMap = new HashMap<>();


    public UMLClassDiagram(String jsonFilePath) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            JSONObject jsonObject = JSON.parseObject(jsonContent);


            this.name = jsonObject.getString("class_diagram");


            jsonObject.getJSONArray("classes").forEach(classObj -> {
                JSONObject classJson = (JSONObject) classObj;
                String className = classJson.getString("name");
                Map<String, String> attrList = new HashMap<>();
                classJson.getJSONArray("attributes").forEach(attrObj -> {
                    JSONObject attrJson = (JSONObject) attrObj;
                    attrList.put(attrJson.getString("attr"), attrJson.getString("type"));
                });
                this.classes.put(className, new UMLClass(className, attrList));
            });


            jsonObject.getJSONArray("associations").forEach(assoClassObj -> {
                JSONObject assoClassJson = (JSONObject) assoClassObj;
                String assoClassName = assoClassJson.getString("name");
                String startClass = assoClassJson.getString("startClass");
                String endClass = assoClassJson.getString("endClass");
                String role = assoClassJson.getString("role");
                String r_Role = assoClassJson.getString("r_role");
                String multi = assoClassJson.getString("multi");
                String r_multi = assoClassJson.getString("r_multi");

                if(assoClassJson.getJSONArray("attributes") != null) {
                    Map<String, String> attrList = new HashMap<>();
                    assoClassJson.getJSONArray("attributes").forEach(attrObj -> {
                        JSONObject attrJson = (JSONObject) attrObj;
                        attrList.put(attrJson.getString("attr"), attrJson.getString("type"));

                    });
                    this.assoClasses.put(assoClassName, new UMLAssoClass(assoClassName, startClass, endClass, role, r_Role, multi, r_multi, true, attrList));
                } else {
                    this.assoClasses.put(assoClassName, new UMLAssoClass(assoClassName, startClass, endClass, role, r_Role, multi, r_multi));
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


        makeClassRoleMap();


    }



    public UMLClassDiagram(String name, Map<String, UMLClass> classes, Map<String, UMLAssoClass> assoClasses) {
        this.classes = classes;
        this.assoClasses = assoClasses;
    }

    public String getName() {
        return name;
    }

    public Map<String, UMLClass> getClasses() {
        return classes;
    }

    public Map<String, UMLAssoClass> getAssoClasses() {
        return assoClasses;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setClasses(Map<String, UMLClass> classes) {
        this.classes = classes;
    }

    public void setAssoClasses(Map<String, UMLAssoClass> assoClasses) {
        this.assoClasses = assoClasses;
    }


    public boolean hasClass(String className) {
        return classes.containsKey(className);
    }


    public OCLValueType getAttrType(String className, String attrName) {

        assert classes.containsKey(className) : "Class " + className + " does not exist in the UML Class Diagram.";

        String type = classes.get(className).getAttrList().get(attrName);
        switch (type) {
            case "Integer":
                return OCLValueType.INTEGER;
            case "Real":
                return OCLValueType.REAL;
            case "Boolean":
                return OCLValueType.BOOLEAN;
            default:
                return OCLValueType.STRING;
        }

    }


    private void makeClassRoleMap() {
        for( String key : assoClasses.keySet()) {
            UMLAssoClass assoClass = assoClasses.get(key);
            String startClass = assoClass.getStartClass();
            String endClass = assoClass.getEndClass();

            if(!classRoleMap.containsKey(startClass)) {
                classRoleMap.put(startClass, new HashMap<>());
            }
            classRoleMap.get(startClass).put(assoClass.getRole(), new Pair<>(assoClass.assoClassName, endClass));


            if(!classRoleMap.containsKey(endClass)) {
                classRoleMap.put(endClass, new HashMap<>());
            }
            classRoleMap.get(endClass).put(assoClass.getR_role(), new Pair<>(assoClass.assoClassName, startClass));
        }
    }


    public String getAssoClassWithRole(String startClass, String role) {
        return classRoleMap.get(startClass).get(role).a;
    }


    public String getAssoEndClassWithRole(String startClass, String role) {
        return classRoleMap.get(startClass).get(role).b;
    }

    public Pair<String, String> getAssoClassAndAssoEndClassWithRole(String startClass, String role) {
        return classRoleMap.get(startClass).get(role);
    }


    public boolean hasRole(String className, String role) {
        return classRoleMap.get(className).containsKey(role);
    }

    public boolean hasAttr(String className, String attr) {
        return classes.get(className).hasAttr(attr);
    }


    public RelationSchema toRelationSchema() {
        Map<String, Relation> relations = new HashMap<>();
        for (String className : classes.keySet()) {
            UMLClass umlClass = classes.get(className);
            List<Pair<String, String>> attributes = new ArrayList<>();
            attributes.add(new Pair<>(className.toLowerCase() + "_id", "String"));

            for (String attr : umlClass.getAttrList().keySet()) {
                attributes.add(new Pair<>(attr, umlClass.getAttrList().get(attr)));
            }
            relations.put(className, new Relation(className, attributes));
        }

        for (String assoClassName : assoClasses.keySet()) {
            UMLAssoClass umlAssoClass = assoClasses.get(assoClassName);
            List<Pair<String, String>> attributes = new ArrayList<>();

            attributes.add(new Pair<>(umlAssoClass.startClass.toLowerCase() + "_id", "String"));
            attributes.add(new Pair<>(umlAssoClass.endClass.toLowerCase() + "_id", "String"));
            if(umlAssoClass.hasAttrs) {
                for (String attr : umlAssoClass.attrList.keySet()) {
                    attributes.add(new Pair<>(attr, umlAssoClass.attrList.get(attr)));
                }
            }
            relations.put(umlAssoClass.assoClassName, new Relation(umlAssoClass.assoClassName, attributes));
        }


        return new RelationSchema(name, relations);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UMLClassDiagram that = (UMLClassDiagram) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }


    @Override
    public String toString() {
        return "UMLClassDiagram{" +
                "name='" + name + '\'' +
                '}';
    }
}
